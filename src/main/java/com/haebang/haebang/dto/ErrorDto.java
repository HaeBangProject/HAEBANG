package com.haebang.haebang.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private int code;
    private String message;
}
