package com.haebang.haebang.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3Resource;
import com.amazonaws.services.s3.model.*;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.S3File;
import com.haebang.haebang.enums.FileCategory;
import com.haebang.haebang.repository.S3FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final S3FileRepository s3FileRepository;

    public S3File uploadFile(MultipartFile multipartFile, Item createdItem) throws IOException{
        log.info("==============s3 service=============");
        String fileName = multipartFile.getOriginalFilename();
        String dirName = "img/"+String.valueOf( createdItem.getId() )+"/";

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

        //object 정보 가져오기
//        ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(bucket);
//        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();
//
//        for (S3ObjectSummary object: objectSummaries) {
//            System.out.println("object = " + object.toString());
//        }

        String s3FileUrl = amazonS3Client.getUrl(bucket, dirName+fileName).toString();

        return saveS3Photo(s3FileUrl, dirName, fileName, createdItem);
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
}
