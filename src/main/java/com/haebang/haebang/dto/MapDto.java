package com.haebang.haebang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data

public class MapDto {
    String address; //주소
    String contract; //계약날짜
    String apart; // 아파트
    String build; //건축년도
    String area; //전용면적
    String amount; //거래금액


}
