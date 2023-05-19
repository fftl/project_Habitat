package com.ssafy.habitat.exception;

import lombok.Getter;

/**
 * Custom Exception 입니다. 저희가 예상하고 정의해놓은 Exception의 경우 모두 이 Exception을 사용하게 됩니다.
 */
@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;

    public CustomException (ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
