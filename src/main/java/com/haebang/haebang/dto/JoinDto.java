package com.haebang.haebang.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class JoinDto {
    @NotBlank(message = "유저이름은 필수 입력값입니다")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    private String password;
    @NotBlank(message = "이메일은 필수 입력값입니다") @Email(message = "이메일 형식을 지켜주세요")
    private String email;
}
