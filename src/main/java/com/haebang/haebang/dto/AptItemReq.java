package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class AptItemReq {
    Long aptId;
    // 지도에서 찾은 아파트 지도 정보
    //...

    String phoneNumber;
    String title;
    String text;
    Long price;
    String dong;
    int floor;

}
