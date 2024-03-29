package com.haebang.haebang.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    INVALID_ACCESS_TOKEN(401, "잘못된 토큰 입니다"), // 토큰이 잘못됐을시 새로 로그인 위한 상태코드 401로 설정
    EXPIRED_ACCESS_TOKEN(401, "만료된 엑세스토큰 입니다"),
    EXPIRED_REFRESH_TOKEN(401, "만료된 리프레시토큰 입니다"),
    EMPTY_ACCESS_TOKEN(401, "토큰이 비어있습니다"),
    ALREADY_JOINED_MEMBER(400, "이미 존재하는 회원이름 입니다"),
    INVALID_MEMBER_INFO(400, "잘못된 회원정보 입니다"),
    UNSUPPORTED_TOKEN(401, "지원하지 않는 토큰입니다"),

    INVALID_EDIT_USER(403, "수정 불가능한 사용자 입니다"),
    INSUFFICIENT_ITEM_INFO(400, "집 내놓기 정보가 부족합니다"),

    LOGOUTED_MEMBER_WARN(403, "경고 : 로그아웃한 회원으로 재로그인 바랍니다"),
    RTK_REISSUE_ERROR(403, "경고 : 재발급 불가 재로그인 바랍니다"),

    STOMP_NO_MATCH_USER(400, "stomp : 토큰과 유저 불일치"),

    S3_PHOTO_NOT_DELETED(500, "사진이 완전히 지워지지 않았습니다");

    private final int code;
    private final String message;
}
