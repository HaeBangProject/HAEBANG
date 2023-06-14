package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class AptItemReq {
    // 아파트 정보
    @NotBlank(message = "도로명 주소는 필수 입력값입니다.")
    String roadAddress;// 도로명주소
    @NotBlank(message = "아파트명은 필수 입력값입니다.")
    String dp;// 아파트명
    @NotBlank(message = "연락처는 필수 입력값입니다.")
    String phoneNumber;
    @NotBlank(message = "제목은 필수 입력값입니다.")
    String title;
    @NotBlank(message = "소개글은 필수 입력값입니다.")
    String text;
    @NotBlank(message = "아파트 동은 필수 입력값입니다.")
    String dong;
    @NotBlank(message = "층 수는 필수 입력값입니다.")
    int floor;
    @NotBlank(message = "계약 연도는 필수 입력값입니다.")
    String contract_year;
    @NotBlank(message = "계약 달은 필수 입력값입니다.")
    String contract_month;
    @NotBlank(message = "계약 일은 필수 입력값입니다.")
    String contract_day;
    @NotBlank(message = "아파트 평수는 필수 입력값입니다.")
    String dp_area;
    @NotBlank(message = "매물 가격은 필수 입력값입니다.")
    String dp_amount;
    @NotBlank(message = "건축연도는 필수 입력값입니다.")
    String build_year;
}
