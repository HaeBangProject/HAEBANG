package com.haebang.haebang.exception;

import com.haebang.haebang.constant.CustomErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final CustomErrorCode customErrorCode;
    public CustomException(CustomErrorCode customErrorCode){
        super(customErrorCode.getMessage());
        this.customErrorCode = customErrorCode;
    }
}
