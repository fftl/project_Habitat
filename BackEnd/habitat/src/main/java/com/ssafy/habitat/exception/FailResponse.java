package com.ssafy.habitat.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FailResponse {

    int status;
    int code;
    String message;

    public FailResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public FailResponse(int code, int status, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public FailResponse(ErrorCode errorCode, String message){
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

}
