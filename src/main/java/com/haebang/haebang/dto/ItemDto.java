package com.haebang.haebang.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Bookmark;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.S3File;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class ItemDto {
    Long id;
    String phoneNumber;
    String title;
    String text;
    String dong;
    int floor;
    long hits;
    LocalDate contract_date;//계약일
    double dp_area;//전용면적
    double dp_amount;//거래금액
    int build_year;//건축년도
    LocalDate createdDate;
    String username;

    Apt apt;
    List<S3File> s3Files;
    List<Bookmark> bookmarks;


    @JsonIgnore
    public ItemDto(Item item){
        this.id = item.getId();
        this.phoneNumber = item.getPhoneNumber();
        this.title = item.getTitle();
        this.text = item.getText();
        this.dong = item.getDong();
        this.floor = item.getFloor();
        this.hits = item.getHits();
        this.contract_date = item.getContract_date();
        this.dp_area = item.getDp_area();
        this.dp_amount = item.getDp_amount();
        this.build_year = item.getBuild_year();
        this.createdDate = item.getCreatedDate();
        this.username = item.getUsername();

        this.apt = item.getApt();
        this.s3Files = item.getS3Files();
        this.bookmarks = item.getBookmarks();
    }
}
