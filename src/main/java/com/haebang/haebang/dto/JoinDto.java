package com.haebang.haebang.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class JoinDto {
    @NotBlank(message = "유저이름은 필수 입력값입니다")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;
}
