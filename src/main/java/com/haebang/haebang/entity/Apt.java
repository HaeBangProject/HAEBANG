package com.haebang.haebang.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // TODO: 도로명주소로 구분
    @Column(unique = true)
    String roadAddress;// 이태원로27가길 49

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

    @OneToMany(mappedBy = "apt", cascade = CascadeType.ALL)
    List<Item> items = new ArrayList<>();
}
