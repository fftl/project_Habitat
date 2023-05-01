package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseFlowerDto {
    private int flower_key;
    private String name;
    private String story;
    private String get_condition;

}
