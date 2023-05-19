package com.ssafy.habitat.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseDrinkLogDto {
    private int drinkLogKey;
    private int drink;
    private Boolean isCoaster;
    private char drinkType;
    private LocalDateTime createdAt;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class responseDrink {
        private LocalDate date;
        private int waterDrink;
        private int nonCafeDrink;
        private int cafeDrink;
    }
}
