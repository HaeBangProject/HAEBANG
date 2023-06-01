package com.haebang.haebang.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
@Data

public class MapDto {
    String address; //주소
    String contract; //계약날짜
    String apart; // 아파트
    String build; //건축년도
    String area; //전용면적
    String amount; //거래금액


    public MapDto(String s, String s1, String s2, String s3, String s4, String s5) {
        this.address = s;
        this.contract= s1;
        this.apart=s2;
        this.build=s3;
        this.area=s4;
        this.amount=s5;
    }
}
