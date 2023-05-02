package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
