package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.service.FriendRequestService;
import com.ssafy.habitat.service.FriendService;
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
@RequestMapping("/friends")
public class FriendController {
    private FriendService friendService;
    private FriendRequestService friendRequestService;
    private UserService userService;
    private DrinkLogService drinkLogService;

    @Autowired
    public FriendController(FriendService friendService, FriendRequestService friendRequestService, UserService userService, DrinkLogService drinkLogService) {
        this.friendService = friendService;
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.drinkLogService = drinkLogService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "친구목록 조회", notes="유저의 친구목록을 조회합니다.")
    public ResponseEntity getFriendList(@RequestParam("user_key") String userKey) {

        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        List<User> friendList = friendService.getFriendList(user); // 유저의 친구목록을 불러옵니다.

        // Entity -> Dto 변환
        List<ResponseUserDto.Friend> friendDtoList = new ArrayList<>();
        for (int i = 0; i < friendList.size(); i++) {
            User curUser = friendList.get(i);
            LocalDateTime last = drinkLogService.getRecentLog(curUser);
            friendDtoList.add(ResponseUserDto.Friend.builder()
                    .user_key(curUser.getUserKey())
                    .nickname(curUser.getNickname())
                    .img_url(curUser.getImgUrl())
                    .recent(last)
                    .build());
        }

        return new ResponseEntity<>(friendDtoList, HttpStatus.OK);
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

    @PostMapping("/request/userkey")
    @ApiOperation(value = "친구신청 보내기(유저키)", notes="해당 유저키의 유저에게 친구신청을 보냅니다.")
    public ResponseEntity sendFriendListByUserKey(@RequestParam("user_key") String userKey, @RequestBody String friendUserKey) {
        User fromUser = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        User toUser = userService.getUser(friendUserKey); // 친구신청할 userKey의 유저를 찾습니다.

        // 친구신청의 유효성(이미 친구 관계에 있진 않은지)을 확인합니다.
        friendService.checkFriendRequestPossible(fromUser, toUser);

        // 친구신청을 보냅니다.
        FriendRequest newFriendRequest = FriendRequest.builder().from(fromUser).to(toUser).status(0).build();
        friendRequestService.addFriendRequest(newFriendRequest);

        // 신청 받은 유저(fromUser)에게 웹소켓 보내기 필요

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/request/code")
    @ApiOperation(value = "친구신청 보내기(친구코드)", notes="해당 친구코드로 친구신청을 보냅니다.")
    public ResponseEntity sendFriendListByCode(@RequestParam("user_key") String userKey, @RequestBody String code) {
        User fromUser = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        User toUser = userService.getByFriendCode(code); // 친구코드에 해당하는 유저를 찾습니다.

        // 친구신청의 유효성(이미 친구 관계에 있진 않은지)을 확인합니다.
        friendService.checkFriendRequestPossible(fromUser, toUser);

        // 친구신청을 보냅니다.
        FriendRequest newFriendRequest = FriendRequest.builder().from(fromUser).to(toUser).status(0).build();
        friendRequestService.addFriendRequest(newFriendRequest);

        // 신청 받은 유저(fromUser)에게 웹소켓 보내기 필요

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/request/ok")
    @ApiOperation(value = "친구신청 수락", notes="해당 친구신청을 수락합니다.")
    public ResponseEntity acceptFriendList(@RequestParam("user_key") String userKey, @RequestBody int requestKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        FriendRequest friendRequest = friendRequestService.getFriendRequestByRequestKey(requestKey); // requestKey의 친구신청 내역을 찾습니다.

        // 해당 유저에게 도착한 친구신청내역인지 확인
        friendRequestService.checkFriendRequestAuthorization(user, friendRequest);
        // 해당 친구신청 내역을 수락으로 변경
        friendRequestService.modifyFriendRequest(friendRequest, 1);

        // 친구 내역 입력
        friendService.addFriend(Friend.builder().myId(friendRequest.getFrom()).friendId(friendRequest.getTo()).build());
        friendService.addFriend(Friend.builder().myId(friendRequest.getTo()).friendId(friendRequest.getFrom()).build());

        // 친구 신청했던 유저(fromUser)에게 친구가 되었음 웹소켓 보내기 필요


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/request/cancel")
    @ApiOperation(value = "친구신청 거절", notes="해당 친구신청을 거절(삭제)합니다.")
    public ResponseEntity refuseFriendList(@RequestParam("user_key") String userKey, @RequestBody int requestKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        FriendRequest friendRequest = friendRequestService.getFriendRequestByRequestKey(requestKey);

        // 해당 유저에게 도착한 친구신청내역인지 확인
        friendRequestService.checkFriendRequestAuthorization(user, friendRequest);
        // 해당 친구신청 내역을 거절로 변경
        friendRequestService.modifyFriendRequest(friendRequest, 2);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/request/all")
    @ApiOperation(value = "친구신청 목록", notes="유저에게 전송된 친구신청 목록을 조회합니다.")
    public ResponseEntity getFriendRequestList(@RequestParam("user_key") String userKey) {

        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.
        List<FriendRequest> friendRequestList = friendRequestService.getFriendRequestList(user); // 유저의 친구신청 목록을 불러옵니다.

        // Entity -> Dto 변환
        List<ResponseUserDto.FriendRequest> friendRequestDtoList = new ArrayList<>();
        for (int i = 0; i < friendRequestList.size(); i++) {
            FriendRequest friendRequest = friendRequestList.get(i);
            User curUser = friendRequest.getFrom();
            LocalDateTime last = drinkLogService.getRecentLog(curUser);
            friendRequestDtoList.add(ResponseUserDto.FriendRequest.builder()
                            .friend_request_key(friendRequest.getFriendRequestKey())
                            .user_key(curUser.getUserKey())
                            .nickname(curUser.getNickname())
                            .img_url(curUser.getImgUrl())
                            .build());
        }

        return new ResponseEntity<>(friendRequestDtoList, HttpStatus.OK);
    }


}