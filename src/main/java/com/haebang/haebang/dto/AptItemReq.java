package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class AptItemReq {
    // 아파트 정보
    @NotBlank(message = "도로명 주소는 필수 입력값입니다.")
    String roadAddress;// 도로명주소
    @NotBlank(message = "아파트명은 필수 입력값입니다.")
    String dp;// 아파트명
    @NotBlank(message = "연락처는 필수 입력값입니다.") @Pattern(regexp = "[0-9]+-[0-9]+-[0-9]+", message = "010-xxxx-xxxx 형식을 맞춰주세요")
    String phoneNumber;
    @NotBlank(message = "제목은 필수 입력값입니다.")
    String title;
    @NotBlank(message = "소개글은 필수 입력값입니다.")
    String text;
    @NotBlank(message = "아파트 동은 필수 입력값입니다.")
    String dong;
    @NotNull(message = "층 수는 필수 입력값입니다.") @Min(0)
    int floor;
    @NotNull(message = "계약 연도는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate contract_date;
    @NotBlank(message = "아파트 평수는 필수 입력값입니다.")
    String dp_area;
    String dp_pyeong;
    @NotNull(message = "매매가는 필수 입력값입니다.")
    double dp_amount;
    @NotNull(message = "건축연도는 필수 입력값입니다.")
    int build_year;

    public void fromEntityToDto(Apt apt, Item item){
        this.roadAddress = apt.getRoadAddress();
        this.dp = apt.getDp();
        this.build_year = item.getBuild_year();
        this.dp_amount = item.getDp_amount();
        this.dp_area = String.valueOf( item.getDp_area() );
        this.contract_date = item.getContract_date();
        this.floor = item.getFloor();
        this.dong = item.getDong();
        this.text = item.getText();
        this.title = item.getTitle();
        this.phoneNumber = item.getPhoneNumber();
    }
}
