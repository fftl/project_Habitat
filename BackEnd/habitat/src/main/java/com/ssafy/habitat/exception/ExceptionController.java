package com.ssafy.habitat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {

    /**
     *  Custom Exception입니다. 우리가 정의한 Error는 해당 위치에서 처리됩니다.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<FailResponse> customException(CustomException ex){
        FailResponse response = new FailResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * 위의 Custom 되지 않고 발생한 Exception은 해당 함수가 받아서 처리합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailResponse> handleException(Exception ex){
        FailResponse response = new FailResponse(ErrorCode.OTHER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.OTHER_ERROR.getStatus()));
    }
}
