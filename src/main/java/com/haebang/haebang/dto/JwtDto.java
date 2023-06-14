package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
