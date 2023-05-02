package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "유저 닉네임 수정", notes="유저의 닉네임을 수정합니다.")
    public ResponseEntity modifiedUser(@RequestParam("userKey") String userKey, @RequestBody Map<String, String> map){
        User user = userService.getUser(userKey);
        String newNickname = map.get("nickname");

        //일단은 null과 공백만 사용할 수 없도록 설정하였습니다.
        if(newNickname == null && newNickname.trim().isEmpty()) {
            throw new CustomException(ErrorCode.NICKNAME_UNAVAILABLE);
        }

        user.setNickname(newNickname);
        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/modify/goal")
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

    @GetMapping
    public ResponseEntity getUser(@RequestParam("userKey") String userKey){
        User user = userService.getUser(userKey);

        ResponseUserDto.User responseUser = ResponseUserDto.User.builder()
                .userKey(user.getUserKey())
                .nickname(user.getNickname())
                .imgUrl(user.getImgUrl())
                .build();

        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }
}
