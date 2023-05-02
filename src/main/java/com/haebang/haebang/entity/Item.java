package com.haebang.haebang.entity;

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
    String ho;
    Long price;
    Long hits;

    @CreatedDate
    Date date;

    String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apt_id")
    Apt apt;

    public void update(AptItemReq req){
        this.phoneNumber = req.getPhoneNumber();
        this.title = req.getTitle();
        this.text = req.getText();
        this.dong = req.getDong();
        this.ho = req.getHo();
        this.price = req.getPrice();
    }
}
