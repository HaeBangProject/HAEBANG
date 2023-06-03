package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.dto.AptItemReq;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String phoneNumber;
    String title;
    String text;
    String dong;
    int floor;
    long hits;
    String contract_year;//계약 날짜
    String contract_month;// 월
    String contract_day;//일
    String dp_area;//전용면적
    String dp_amount;//거래금액
    String build_year;//건축년도

    @CreationTimestamp
    LocalDateTime createdDate;

    String username;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "apt_id")
    Apt apt;

    public void update(AptItemReq req){
        this.phoneNumber = req.getPhoneNumber();
        this.title = req.getTitle();
        this.text = req.getText();
        this.dong = req.getDong();
        this.floor = req.getFloor();
        this.dp_amount = req.getDp_amount();
        this.dp_area = req.getDp_area();
        this.build_year = req.getBuild_year();
        this.contract_year = req.getContract_year();
        this.contract_month = req.getContract_month();
        this.contract_day = req.getContract_day();
    }
}
