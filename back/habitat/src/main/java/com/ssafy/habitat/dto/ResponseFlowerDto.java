package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseFlowerDto {
    private int flowerKey;
    private String name;
    private String story;
    private String getCondition;

}
