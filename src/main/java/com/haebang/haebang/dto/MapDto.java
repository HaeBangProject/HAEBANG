package com.haebang.haebang.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
@Data
public class MapDto {
    List<String> address; //주소
    List<String> contract; //계약날짜
    List<String> apart; // 아파트
    List<String> build; //건축년도
    List<String> area; //전용면적
    List<String> amount; //거래금액
}
