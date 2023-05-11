package com.haebang.haebang.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String value1;//법정 주소
    String code_main;//법정동본번코드
    String code_sub;//법정동부번코드
    String contract_year;//계약 날짜
    String contract_month;// 월
    String contract_day;//일
    String build_year;//건축년도
    String dp_area;//전용면적
    String dp_amount;//거래금액
    String dp;//아파트

}
