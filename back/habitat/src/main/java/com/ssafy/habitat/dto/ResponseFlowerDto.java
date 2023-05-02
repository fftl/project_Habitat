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

    @Setter
    @Getter
    @Builder
    public static class Collection {
        private int flowerKey;
        private String name;
        private String story;
        private String getCondition;
        private int userStatus; // 획득 여부(0 : 미획득, 1: 획득가능, 2: 획득)
    }

}
