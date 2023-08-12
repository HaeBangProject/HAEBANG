package com.haebang.haebang.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.S3File;
import com.haebang.haebang.enums.FileCategory;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.S3FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final S3FileRepository s3FileRepository;

    public S3File uploadFile(MultipartFile multipartFile, Item item) throws IOException{
        log.info("==============s3 service=============");
        String fileName = multipartFile.getOriginalFilename();
        String dirName = "img/"+String.valueOf( item.getId() )+"/";

        String fileFormatName = multipartFile.getContentType()
                .substring(multipartFile.getContentType().lastIndexOf("/") + 1);

        // 이미지 크기 줄이기
        multipartFile = resize(multipartFile, fileFormatName, fileName,612);

        //파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = "";

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        switch (ext) {
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "csv":
                contentType = "text/csv";
                break;
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            amazonS3Client.putObject(new PutObjectRequest(bucket, dirName+fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            log.info("s3 upload ok!");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        String s3FileUrl = amazonS3Client.getUrl(bucket, dirName+fileName).toString();

        return saveS3Photo(s3FileUrl, dirName, fileName, item);
    }

    public MultipartFile resize(MultipartFile originalImage, String fileFormatName, String fileName, int targetWidth){
        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            // newWidth : newHeight = originWidth : originHeight
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
            if(originWidth < targetWidth)
                return originalImage;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }

    public S3File saveS3Photo(String s3FileUrl, String dirName, String fileName, Item createdItem){
        S3File s3File = S3File.builder()
                .s3Url(s3FileUrl)
                .fileCategory(FileCategory.PHOTO)
                .dirName(dirName)
                .fileName(fileName)
                .item(createdItem)
                .build();
        return s3FileRepository.save(s3File);
    }

    public void deletePhoto(Long id) {
        try {
            S3File s3File = s3FileRepository.findById(id).orElseThrow();
            amazonS3Client.deleteObject(bucket, s3File.getDirName() + s3File.getFileName());
            s3FileRepository.delete(s3File);
        } catch (Exception e){
            throw new CustomException(CustomErrorCode.S3_PHOTO_NOT_DELETED);
        }
    }
}
