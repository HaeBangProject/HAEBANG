package com.haebang.haebang.exception;

import com.haebang.haebang.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity customExceptionHandler(CustomException e){
        return new ResponseEntity(
                ErrorDto.builder()
                        .code(e.getCustomErrorCode().getCode())
                        .message(e.getCustomErrorCode().getMessage())
                        .build(),
                HttpStatus.valueOf(e.getCustomErrorCode().getCode())
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity exceptionHandler(Exception e){
        return new ResponseEntity(
                ErrorDto.builder()
                        .code(500)
                        .message(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
