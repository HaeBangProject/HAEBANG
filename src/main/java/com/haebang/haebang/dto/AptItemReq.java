package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class AptItemReq {
    // 아파트 정보
    String roadAddress;// 도로명주소
    String dp;// 아파트명
    String phoneNumber;
    String title;
    String text;
    String dong;
    int floor;
    String contract_year;
    String contract_month;
    String contract_day;
    String dp_area;
    String dp_amount;
    String build_year;
}
