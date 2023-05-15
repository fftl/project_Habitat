package com.ssafy.habitat.controller;

import com.ssafy.habitat.config.TokenProvider;
import com.ssafy.habitat.dto.RequestDrinkLogDto;
import com.ssafy.habitat.dto.ResponseDrinkLogDto;
import com.ssafy.habitat.entity.Collection;
import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.service.DrinkLogService;
import com.ssafy.habitat.service.RewardService;
import com.ssafy.habitat.service.StreakLogService;
import com.ssafy.habitat.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/drinkLogs")
public class DrinkLogController {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private DrinkLogService drinkLogService;
    private UserService userService;
    private RewardService rewardService;
    private StreakLogService streakLogService;
    private TokenProvider tokenProvider;

    @Autowired
    public DrinkLogController(DrinkLogService drinkLogService, UserService userService, RewardService rewardService, StreakLogService streakLogService, TokenProvider tokenProvider) {
        this.drinkLogService = drinkLogService;
        this.userService = userService;
        this.rewardService = rewardService;
        this.streakLogService = streakLogService;
        this.tokenProvider = tokenProvider;
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
    public ResponseEntity getDrinkLog(HttpServletRequest request) {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        List<DrinkLog> drinkLogList = drinkLogService.getAllLogs(user);

        // Entity -> Dto 변환
        List<ResponseDrinkLogDto> drinkLogDtoList = new ArrayList<>();
        for (int i = 0; i < drinkLogList.size(); i++) {
            DrinkLog drinkLog = drinkLogList.get(i);

            drinkLogDtoList.add(ResponseDrinkLogDto.builder()
                            .drinkLogKey(drinkLog.getDrinkLogKey())
                            .drink(drinkLog.getDrink())
                            .drinkType(drinkLog.getDrinkType())
                            .isCoaster(drinkLog.isCoaster())
                            .createdAt(drinkLog.getCreatedAt())
                    .build());
        }

        return new ResponseEntity<>(drinkLogDtoList, HttpStatus.OK);
    }

    @GetMapping("/day")
    @ApiOperation(value = "일일 섭취로그 조회", notes="유저의 오늘의 섭취로그를 조회합니다.")
    public ResponseEntity getDailyDrinkLog(HttpServletRequest request) {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        List<DrinkLog> drinkLogList = drinkLogService.getDailyLogs(user); //찾아낸 유저의 오늘 섭취로그를 가져옵니다.

        // Entity -> Dto 변환
        List<ResponseDrinkLogDto> drinkLogDtoList = new ArrayList<>();
        for (int i = 0; i < drinkLogList.size(); i++) {
            DrinkLog drinkLog = drinkLogList.get(i);

            drinkLogDtoList.add(ResponseDrinkLogDto.builder()
                    .drinkLogKey(drinkLog.getDrinkLogKey())
                    .drink(drinkLog.getDrink())
                    .drinkType(drinkLog.getDrinkType())
                    .isCoaster(drinkLog.isCoaster())
                    .createdAt(drinkLog.getCreatedAt())
                    .build());
        }
        return new ResponseEntity<>(drinkLogDtoList, HttpStatus.OK);
    }

    @GetMapping("/week/total")
    @ApiOperation(value = "최근 일주일 섭취로그 조회", notes="유저의 최근 1주일의 섭취로그를 조회합니다.")
    public ResponseEntity getWeekDrinkLog(HttpServletRequest request) {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        List<DrinkLog> drinkLogList = drinkLogService.getWeeklyLogs(user); //찾아낸 유저의 오늘 섭취로그를 가져옵니다.

        HashMap<LocalDate, ResponseDrinkLogDto.responseDrink> map = new HashMap<>();
        for (int i = 0; i <drinkLogList.size() ; i++) {
            DrinkLog drinkLog = drinkLogList.get(i);
            LocalDate localDate = drinkLog.getCreatedAt().toLocalDate();
            if(!map.containsKey(localDate)){
                map.put(localDate, ResponseDrinkLogDto.responseDrink.builder()
                                .date(localDate)
                                .cafeDrink(0)
                                .waterDrink(0)
                                .nonCafeDrink(0).build());
                if(drinkLog.getDrinkType() == 'w') map.get(localDate).setWaterDrink(map.get(localDate).getWaterDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'c') map.get(localDate).setCafeDrink(map.get(localDate).getCafeDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'n') map.get(localDate).setNonCafeDrink(map.get(localDate).getNonCafeDrink()+drinkLog.getDrink());
            } else {
                if(drinkLog.getDrinkType() == 'w') map.get(localDate).setWaterDrink(map.get(localDate).getWaterDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'c') map.get(localDate).setCafeDrink(map.get(localDate).getCafeDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'n') map.get(localDate).setNonCafeDrink(map.get(localDate).getNonCafeDrink()+drinkLog.getDrink());
            }
        }

        ArrayList<ResponseDrinkLogDto.responseDrink> response = new ArrayList<>();
        for (LocalDate date : map.keySet()) {
            response.add(map.get(date));
        }

        Collections.sort(response, new Comparator<ResponseDrinkLogDto.responseDrink>() {
            @Override
            public int compare(ResponseDrinkLogDto.responseDrink o1, ResponseDrinkLogDto.responseDrink o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/month/total")
    @ApiOperation(value = "최근 한 달 섭취로그 조회", notes="유저의 최근 한달의 섭취로그를 조회합니다.")
    public ResponseEntity getMonthDrinkLog(HttpServletRequest request) {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        List<DrinkLog> drinkLogList = drinkLogService.getMonthlyLogs(user); //찾아낸 유저의 오늘 섭취로그를 가져옵니다.

        HashMap<LocalDate, ResponseDrinkLogDto.responseDrink> map = new HashMap<>();
        for (int i = 0; i <drinkLogList.size() ; i++) {
            DrinkLog drinkLog = drinkLogList.get(i);
            LocalDate localDate = drinkLog.getCreatedAt().toLocalDate();
            if(!map.containsKey(localDate)){
                map.put(localDate, ResponseDrinkLogDto.responseDrink.builder()
                        .date(localDate)
                        .cafeDrink(0)
                        .waterDrink(0)
                        .nonCafeDrink(0).build());
                if(drinkLog.getDrinkType() == 'w') map.get(localDate).setWaterDrink(map.get(localDate).getWaterDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'c') map.get(localDate).setCafeDrink(map.get(localDate).getCafeDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'n') map.get(localDate).setNonCafeDrink(map.get(localDate).getNonCafeDrink()+drinkLog.getDrink());
            } else {
                if(drinkLog.getDrinkType() == 'w') map.get(localDate).setWaterDrink(map.get(localDate).getWaterDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'c') map.get(localDate).setCafeDrink(map.get(localDate).getCafeDrink()+drinkLog.getDrink());
                if(drinkLog.getDrinkType() == 'n') map.get(localDate).setNonCafeDrink(map.get(localDate).getNonCafeDrink()+drinkLog.getDrink());
            }
        }

        ArrayList<ResponseDrinkLogDto.responseDrink> response = new ArrayList<>();
        for (LocalDate date : map.keySet()) {
            response.add(map.get(date));
        }

        Collections.sort(response, new Comparator<ResponseDrinkLogDto.responseDrink>() {
            @Override
            public int compare(ResponseDrinkLogDto.responseDrink o1, ResponseDrinkLogDto.responseDrink o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/day/total")
    @ApiOperation(value = "일일 누적음수량 조회", notes="유저의 오늘의 누적 음수량을 조회합니다.")
    public ResponseEntity getDailyTotalDrinkLog(HttpServletRequest request) {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        int totalDrink = drinkLogService.getDailyTotalDrink(user);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("totalDrink", totalDrink);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/add")
    @ApiOperation(value = "섭취량 증가(수동)", notes="수동으로 유저의 음수량을 입력합니다.")
    public ResponseEntity addDrinkLog(HttpServletRequest request, @RequestBody RequestDrinkLogDto requestDrinkLog) throws IOException {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // Dto -> Entity
        DrinkLog newDrinkLog = DrinkLog.builder()
                .drink(requestDrinkLog.getDrink())
                .drinkType(requestDrinkLog.getDrinkType())
                .isCoaster(false)
                .isRemoved(false)
                .user(user)
                .build();

        // 섭취량 로그 등록
        drinkLogService.addDrinkLog(newDrinkLog);
        rewardService.addPlantingExp(user, newDrinkLog);

        // 누적섭취량 증가에 따른 해금 확인
        rewardService.checkDrinkUnlock(user);

        // 누적섭취량 증가에 따른 오늘의 목표달성 확인
        if(rewardService.checkStreakSuccess(user)) {
            // 스트릭 증가
            streakLogService.addStreakLog(user);
            // 스트릭 변화에 따른 해금 확인
            rewardService.checkStreakUnlock(user);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auto")
    @ApiOperation(value = "섭취량 증가(코스터)", notes="코스터로 섭취한 음수량을 입력합니다.")
    public ResponseEntity addAutoDrinkLog(HttpServletRequest request, @RequestBody RequestDrinkLogDto requestDrinkLog) throws IOException {
        String userKey = tokenProvider.getUserKey(request.getHeader(AUTHORIZATION_HEADER));
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // Dto -> Entity
        DrinkLog newDrinkLog = DrinkLog.builder()
                .drink(requestDrinkLog.getDrink())
                .drinkType(requestDrinkLog.getDrinkType())
                .isCoaster(true)
                .isRemoved(false)
                .user(user)
                .build();

        // 섭취량 로그 등록
        drinkLogService.addDrinkLog(newDrinkLog);

        // 누적섭취량 증가에 따른 해금 확인
        rewardService.checkDrinkUnlock(user);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "섭취량 수정", notes="섭취량을 수정합니다.")
    public ResponseEntity modifiedDrinkLog(@PathVariable("drinkLogKey") int drinkLogKey, @RequestBody RequestDrinkLogDto.ModifyDrink requestDrinkLogDto) {
        DrinkLog drinkLog = drinkLogService.getLog(drinkLogKey);
        drinkLog.setDrink(requestDrinkLogDto.getDrink());
        drinkLogService.addDrinkLog(drinkLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "섭취량 삭제", notes="섭취량을 삭제처리 합니다.")
    public ResponseEntity deleteDrinkLog(@PathVariable("drinkLogKey") int drinkLogKey) {
        DrinkLog drinkLog = drinkLogService.getLog(drinkLogKey);
        drinkLog.setRemoved(true);
        drinkLogService.addDrinkLog(drinkLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
