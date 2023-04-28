package com.ssafy.habitat.controller;

import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.service.LogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(name = "/logs")
public class LogController {
    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/success")
    @ApiOperation(value = "스웨거 성공 테스트", notes="스웨거가 작동하는지 테스트합니다.")
    public ResponseEntity getTest(){
        HttpStatus status = HttpStatus.OK;
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", "동밍");
        map.put("img_url", "http://localhost8837");
        map.put("friend_code", "AJ48ahisHIAS88Z");
        return new ResponseEntity<>(map, status);
    }

    @GetMapping("/fail")
    @ApiOperation(value = "스웨거 실패 테스트", notes="스웨거가 작동하는지 테스트합니다.")
    public ResponseEntity getTest2(){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        boolean test =  true;
        if(test){
            throw new CustomException(ErrorCode.FRIEND_CODE_NOT_FOUND);
        }
        return new ResponseEntity<>("하하", status);
    }
}
