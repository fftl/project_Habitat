package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ResponseUserDto {

    @Setter
    @Getter
    @Builder
    public static class Friend {
        private String user_key;
        private String nickname; // 닉네임
        private String img_url; // 프로필사진
        private LocalDateTime recent; // 마지막 음수시간
    }

    @Setter
    @Getter
    @Builder
    public static class FriendRequest {
        private int friend_request_key;
        private String user_key;
        private String nickname; // 닉네임
        private String img_url; // 프로필사진
    }
}