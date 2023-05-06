package com.haebang.haebang.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
@Data
public class MapDto {
    List<String> address;
    List<String> contract;
    List<String> apart;
    List<String> build;
    List<String> area;
    List<String> amount;
}
