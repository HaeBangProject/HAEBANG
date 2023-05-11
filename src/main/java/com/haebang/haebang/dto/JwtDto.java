package com.haebang.haebang.dto;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class JwtDto {
    String grantType;
    String accessToken;
    String refreshToken;
}
