package com.haebang.haebang.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    INVALID_ACCESS_TOKEN(401, "잘못된 토큰 입니다"),
    EXPIRED_ACCESS_TOKEN(401, "만료된 토큰 입니다"),
    EMPTY_ACCESS_TOKEN(400, "토큰이 비어있습니다"),
    ALREADY_JOINED_MEMBER(400, "이미 존재하는 회원이름 입니다"),
    INVALID_MEMBER_INFO(400, "잘못된 회원정보 입니다"),
    UNSUPPORTED_TOKEN(401, "지원하지 않는 토큰입니다"),

    INVALID_EDIT_USER(403, "수정 불가능한 사용자 입니다"),
    INSUFFICIENT_ITEM_INFO(400, "집 내놓기 정보가 부족합니다"),

    LOGOUTED_MEMBER_WARN(403, "경고 : 로그아웃한 회원으로 재로그인 바랍니다"),
    RTK_REISSUE_ERROR(403, "경고 : 재발급 불가 재로그인 바랍니다"),

    INVALID_FORMAT_ADDRESS(400, "도로명 주소를 다시 작성해 주세요. format : '000로(00길) 00'"),
    STOMP_NO_MATCH_USER(400, "stomp : 토큰과 유저 불일치");

    private final int code;
    private final String message;
}
