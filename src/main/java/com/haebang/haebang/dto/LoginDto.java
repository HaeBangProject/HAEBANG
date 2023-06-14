package com.haebang.haebang.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginDto {
    private String username;
    private String password;
}