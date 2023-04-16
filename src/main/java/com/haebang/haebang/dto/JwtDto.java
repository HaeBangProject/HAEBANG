package com.haebang.haebang.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDto {
    String grantType;
    String accessToken;
    String refreshToken;
    // TODO: 토큰 재발급, 로그아웃,
}
