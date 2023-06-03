package com.haebang.haebang.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    String dp;//아파트

    @OneToMany(mappedBy = "apt", cascade = CascadeType.ALL)
    List<Item> items = new ArrayList<>();
}
