package com.haebang.haebang.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    INVALID_ACCESS_TOKEN(000, "잘못된 토큰 입니다"),
    EXPIRED_ACCESS_TOKEN(000, "만료된 토큰 입니다"),
    EMPTY_ACCESS_TOKEN(000, "토큰이 비어있습니다"),
    ALREADY_JOINED_MEMBER(000, "이미 가입한 회원입니다"),
    INVALID_MEMBER_INFO(000, "잘못된 회원정보 입니다"),
    UNSUPPORTED_TOKEN(000, "지원하지 않는 토큰입니다");
    final private int code;
    final private String message;
}
