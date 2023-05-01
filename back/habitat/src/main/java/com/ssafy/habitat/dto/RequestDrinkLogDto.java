package com.ssafy.habitat.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestDrinkLogDto {
    private int drink;
    private char drinkType;

}
