package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.UserDto;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.service.FriendRequestService;
import com.ssafy.habitat.service.FriendService;
import com.ssafy.habitat.service.LogService;
import com.ssafy.habitat.service.UserService;
import com.ssafy.habitat.util.FailResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(name = "/friends")
public class FriendController {
    private FriendService friendService;
    private FriendRequestService friendRequestService;
    private UserService userService;
    private LogService logService;

    @Autowired
    public FriendController(FriendService friendService, FriendRequestService friendRequestService, UserService userService, LogService logService) {
        this.friendService = friendService;
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.logService = logService;
    }

    @GetMapping("/code")
    @ApiOperation(value = "친구코드 조회", notes="유저의 친구코드를 조회합니다.")
    public ResponseEntity getFriendCode(@RequestParam("user_key") String userKey) {

        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // 찾아온 유저의 친구코드를 불러와 리턴합니다.
        HashMap<String, String> map = new HashMap<>();
        String friendCode = user.getFriendCode();

        map.put("friend_code", friendCode);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @GetMapping("/all")
    @ApiOperation(value = "친구목록 조회", notes="유저의 친구목록을 조회합니다.")
    public ResponseEntity getFriendList(@RequestParam("user_key") String userKey) {

        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        List<User> friendList = friendService.getFriendList(user); // 유저의 친구목록을 불러옵니다.

        // Entity -> Dto 변환
        List<UserDto.Friend> friendDtoList = new ArrayList<>();
        for (int i = 0; i < friendList.size(); i++) {
            User curUser = friendList.get(i);
            LocalDateTime last = logService.getRecentLog(curUser);
            friendDtoList.add(UserDto.Friend.builder()
                            .userKey(curUser.getUserKey())
                            .nickname(curUser.getNickname())
                            .imgUrl(curUser.getImgUrl())
                            .recent(last)
                    .build());
        }

        return new ResponseEntity<>(friendDtoList, HttpStatus.OK);

    }
}