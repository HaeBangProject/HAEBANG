package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class AptItemReq {
    // 아파트 정보
    Long aptId;
    String roadAddress;
    String dp;// 아파트 이름

    // 매물 정보
    String phoneNumber;
    String title;
    String text;
    Long price;
    String dong;
    int floor;

}
