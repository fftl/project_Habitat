package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseExpDto {
    private int flower_key;
    private int exp;
    private int max_exp;
    private int lv;
}
