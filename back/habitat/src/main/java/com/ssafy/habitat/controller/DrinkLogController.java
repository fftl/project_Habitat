package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.RequestDrinkLogDto;
import com.ssafy.habitat.dto.ResponseDrinkLogDto;
import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.service.DrinkLogService;
import com.ssafy.habitat.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class DrinkLogController {
    private DrinkLogService drinkLogService;
    private UserService userService;

    @Autowired
    public DrinkLogController(DrinkLogService drinkLogService, UserService userService) {
        this.drinkLogService = drinkLogService;
        this.userService = userService;
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

    @GetMapping("/all")
    @ApiOperation(value = "섭취로그 조회", notes="유저의 섭취로그를 조회합니다.")
    public ResponseEntity getDrinkLog(@RequestParam("userKey") String userKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        List<DrinkLog> drinkLogList = drinkLogService.getAllLogs(user);

        // Entity -> Dto 변환
        List<ResponseDrinkLogDto> drinkLogDtoList = new ArrayList<>();
        for (int i = 0; i < drinkLogList.size(); i++) {
            DrinkLog drinkLog = drinkLogList.get(i);

            drinkLogDtoList.add(ResponseDrinkLogDto.builder()
                            .logKey(drinkLog.getLogKey())
                            .drink(drinkLog.getDrink())
                            .drinkType(drinkLog.getDrinkType())
                            .isCoaster(drinkLog.isCoaster())
                            .createdAt(drinkLog.getCreatedAt())
                    .build());
        }

        return new ResponseEntity<>(drinkLogDtoList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "섭취량 증가(수동)", notes="수동으로 유저의 음수량을 입력합니다.")
    public ResponseEntity addDrinkLog(@RequestParam("userKey") String userKey, @RequestBody RequestDrinkLogDto requestDrinkLog) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // Dto -> Entity
        DrinkLog newDrinkLog = DrinkLog.builder()
                .drink(requestDrinkLog.getDrink())
                .drinkType(requestDrinkLog.getDrinkType())
                .isCoaster(false)
                .isRemoved(false)
                .user(user)
                .build();

        drinkLogService.addDrinkLog(newDrinkLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auto")
    @ApiOperation(value = "섭취량 증가(코스터)", notes="코스터로 섭취한 음수량을 입력합니다.")
    public ResponseEntity addAutoDrinkLog(@RequestParam("userKey") String userKey, @RequestBody RequestDrinkLogDto requestDrinkLog) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // Dto -> Entity
        DrinkLog newDrinkLog = DrinkLog.builder()
                .drink(requestDrinkLog.getDrink())
                .drinkType(requestDrinkLog.getDrinkType())
                .isCoaster(true)
                .isRemoved(false)
                .user(user)
                .build();

        drinkLogService.addDrinkLog(newDrinkLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
