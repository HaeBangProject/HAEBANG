package com.haebang.haebang.dto;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtDto {
    @Builder.Default
    String grantType = "Bearer";
    String accessToken;
    String refreshToken;
    String username;
}
