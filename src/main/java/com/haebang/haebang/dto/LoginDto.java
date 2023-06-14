package com.haebang.haebang.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginDto {
    @NotBlank(message = "유저이름은 필수 입력값입니다")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    private String password;
}