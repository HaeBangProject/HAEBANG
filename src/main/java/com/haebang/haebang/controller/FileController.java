package com.haebang.haebang.controller;

import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/file")
public class FileController {
    private final S3Service s3Service;
    // 삭제, 수정-aptcontroller에서
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id")Long id) {
        s3Service.deletePhoto(id);
        return ResponseEntity.ok("삭제 완료");
    }
}
