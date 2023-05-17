package com.ssafy.habitat.dto;

import lombok.*;

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
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Collection {
        private int flowerKey;
        private String name;
        private String story;
        private String getCondition;
        private int userStatus; // 획득 여부(0 : 미획득, 1: 획득가능, 2: 획득)
    }

}
