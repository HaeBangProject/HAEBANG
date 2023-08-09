package com.haebang.haebang.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haebang.haebang.entity.S3File;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class AptItemRes {
    @JoinColumn(name = "data")
    AptItemReq aptItemReq;
    List<S3File> s3Files = new ArrayList<>();
}
