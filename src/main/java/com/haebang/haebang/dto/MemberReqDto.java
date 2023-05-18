package com.haebang.haebang.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Data
public class MemberReqDto {
    private String username;
    private String password;
    private String email;

    MemberReqDto(String username, String password, String email){
        this.username = username; this.password = password; this.email = email;
    }
}
