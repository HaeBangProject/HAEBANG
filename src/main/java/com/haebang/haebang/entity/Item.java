package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haebang.haebang.dto.AptItemReq;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
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
    Long price;
    Long hits;

    @CreatedDate
    Date date;

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
        this.price = req.getPrice();
    }
}
