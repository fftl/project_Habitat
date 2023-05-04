package com.ssafy.habitat.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDrinkLogDto {
    private int drink;
    private char drinkType;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ModifyDrink {
        private int drink;
    }

}
