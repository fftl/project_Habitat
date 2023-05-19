package com.ssafy.habitat.dto;

import lombok.*;

public class RequestUserDto {
    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestFriend {
        private String friendUserKey;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyNickname {
        private String nickname;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyGoal {
        private int goal;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendCode {
        private String friendCode;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String socialKey;
        private int socialType;
    }
}
