package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserCoaster;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.service.CoasterService;
import com.ssafy.habitat.service.S3Uploader;
import com.ssafy.habitat.service.UserCoasterService;
import com.ssafy.habitat.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private S3Uploader s3Uploader;
    private CoasterService coasterService;
    private UserCoasterService userCoasterService;

    @Autowired
    public UserController(UserService userService, S3Uploader s3Uploader, CoasterService coasterService, UserCoasterService userCoasterService) {
        this.userService = userService;
        this.s3Uploader = s3Uploader;
        this.coasterService = coasterService;
        this.userCoasterService = userCoasterService;
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "유저 닉네임 수정", notes="유저의 닉네임을 수정합니다.")
    public ResponseEntity modifiedUser(@RequestParam("userKey") String userKey, @RequestBody Map<String, String> map){
        User user = userService.getUser(userKey);
        String newNickname = map.get("nickname");

        //일단은 null과 공백만 사용할 수 없도록 설정하였습니다.
        if(newNickname == null || newNickname.trim().isEmpty() || newNickname.equals("")) {
            throw new CustomException(ErrorCode.NICKNAME_UNAVAILABLE);
        }

        user.setNickname(newNickname);
        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/modify/goal")
    @ApiOperation(value = "유저 목표 섭취량 수정", notes="유저의 목표섭취량을 수정합니다.")
    public ResponseEntity modifiedUserGoal(@RequestParam("userKey") String userKey, @RequestBody Map<String, Integer> map){
        User user = userService.getUser(userKey);
        int newGoal = map.get("goal");

        //새로운 목표 음수량을 설정합니다. 현재는 음수만 불가능하도록 만들었습니다.
        if(newGoal < 0) {
            throw new CustomException(ErrorCode.GOAL_UNAVAILABLE);
        }

        user.setGoal(newGoal);
        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/modify/img")
    @ApiOperation(value = "유저 이미지 수정", notes="유저의 프로필 이미지를 수정합니다.")
    public ResponseEntity modifiedUserImg(@RequestParam("userKey") String userKey, @RequestParam("file") MultipartFile file) throws IOException {

        User user = userService.getUser(userKey);

        //파일의 확장자를 탐색합니다. ( 일단 후 순위 )
        String imgUrl = s3Uploader.uploadFile(file, userKey);

        user.setImgUrl(imgUrl);
        userService.addUser(user);

        System.out.println("test");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "유저 조회", notes="유저 키를 통해 유저를 조회합니다.")
    public ResponseEntity getUser(@RequestParam("userKey") String userKey){
        User user = userService.getUser(userKey);

        ResponseUserDto.User responseUser = ResponseUserDto.User.builder()
                .userKey(user.getUserKey())
                .nickname(user.getNickname())
                .imgUrl(user.getImgUrl())
                .goal(user.getGoal())
                .build();

        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @PostMapping("/coaster")
    @ApiOperation(value = "유저 코스터 등록", notes="유저의 코스터를 등록합니다.")
    public ResponseEntity getUser(@RequestParam("userKey") String userKey, @RequestBody Map<String, String> map){
        User user = userService.getUser(userKey);
        Coaster coaster = coasterService.getCoaster(map.get("coasterKey"));

        UserCoaster userCoaster = UserCoaster.builder()
                .coaster(coaster)
                .user(user)
                .build();

        userCoasterService.addUserCoaster(userCoaster);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "유저 로그인", notes="유저 로그인 처리를 합니다.")
    public ResponseEntity login(@RequestParam("socialKey") String socialKey){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
