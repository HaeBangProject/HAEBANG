package com.haebang.haebang.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MemberReqDto {
    private String username;
    private String password;
}
