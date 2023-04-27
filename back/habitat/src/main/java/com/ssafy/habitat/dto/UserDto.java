package com.ssafy.habitat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class UserDto {

    @Setter
    @Getter
    @Builder
    public static class Friend {
        private String userKey;
        private String nickname; // 닉네임
        private String imgUrl; // 프로필사진
        private LocalDateTime recent; // 마지막 음수시간
    }
}