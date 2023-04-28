package com.ssafy.habitat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_KEY_NOT_FOUND(404, -404001, "유저 아이디로 찾을 수 있는 유저가 없습니다."),
    FRIEND_CODE_NOT_FOUND(404, -404002, "해당 친구코드는 존재하지 않습니다."),

    OTHER_ERROR(500, -500000, "서버 에러, 서버에서 아직 정의되지 않은 에러입니다.")
    ;

    private int status;
    private int code;
    private String message;
}