package com.haebang.haebang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true)
    String roadAddress;// 서울 00구 이태원로27가길 49

    String dp;// 한 건물당 하나의 도로명주소가짐 (아파트/건물이름)
    Long cnt;

    @JsonIgnore
    @OneToMany(mappedBy = "apt", cascade = CascadeType.ALL)
    List<Item> items = new ArrayList<>();

    public void increaseCnt(){
        this.cnt += 1;
    }
    public void decreaseCnt(){
        this.cnt -= 1;
    }
}
