package com.haebang.haebang.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class JoinDto {
    private String username;
    private String password;
    private String email;
}
