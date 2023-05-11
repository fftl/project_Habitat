package com.ssafy.habitat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ResponseUserDto {

    @Setter
    @Getter
    @Builder
    public static class User {
        private String userKey;
        private String nickname; // 닉네임
        private String imgUrl; // 프로필  사진
        private int goal;
    }

    @Setter
    @Getter
    @Builder
    public static class Friend {
        private String userKey;
        private String nickname; // 닉네임
        private String imgUrl; // 프로필사진
        private LocalDateTime recent; // 마지막 음수시간
    }

    @Setter
    @Getter
    @Builder
    public static class FriendRequest {
        private int friendRequestKey;
        private String userKey;
        private String nickname; // 닉네임
        private String imgUrl; // 프로필사진
    }

    @Setter
    @Getter
    @Builder
    public static class LoginReq {
        private int friendRequestKey;
        private String userKey;
        private String nickname; // 닉네임
        private String imgUrl; // 프로필사진
    }

    @Setter
    @Getter
    @Builder
    public static class TokenResponse {
        private String AccessToken;
        private String RefreshToken;
    }
}