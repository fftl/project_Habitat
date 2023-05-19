package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseExpDto {
    private int flowerKey;
    private int exp;
    private int maxExp;
    private int lv;
}
