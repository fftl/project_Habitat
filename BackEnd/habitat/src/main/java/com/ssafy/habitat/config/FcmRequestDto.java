package com.ssafy.habitat.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FcmRequestDto {
    private String targetToken;
    private String title;
    private String body;
}
