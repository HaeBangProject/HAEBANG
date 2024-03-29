package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.dto.AptItemReq;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
    LocalDate contract_date;//계약일
    double dp_area;//전용면적
    double dp_amount;//거래금액
    int build_year;//건축년도

    @CreationTimestamp
    LocalDate createdDate;

    String username;

    @ManyToOne
    @JoinColumn(name = "apt_id")// casecadeType = ALL 코드 지워서 부모까지 지워지는거 막음
    Apt apt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    Member member;

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    List<S3File> s3Files = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    List<Bookmark> bookmarks = new ArrayList<>();

    @JsonIgnore
    public void update(AptItemReq req){
        this.phoneNumber = req.getPhoneNumber();
        this.title = req.getTitle();
        this.text = req.getText();
        this.dong = req.getDong();
        this.floor = req.getFloor();
        this.dp_amount = req.getDp_amount();
        this.dp_area = Double.parseDouble( req.getDp_area() );
        this.build_year = req.getBuild_year();
        this.contract_date = req.getContract_date();
    }

}
