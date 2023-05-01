package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseDrinkLogDto {
    private int log_key;
    private int drink;
    private boolean is_coaster;
    private char drink_type;
    private LocalDateTime created_at;
}
