package com.ssafy.habitat.controller;

import com.ssafy.habitat.config.TokenInfo;
import com.ssafy.habitat.config.TokenProvider;
import com.ssafy.habitat.dto.RequestCoasterDto;
import com.ssafy.habitat.dto.RequestUserDto;
import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserCoaster;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.service.*;
import com.ssafy.habitat.utils.Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
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
    private RewardService rewardService;
    private TokenProvider tokenProvider;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private Util util;

    @Autowired
    public UserController(UserService userService, S3Uploader s3Uploader, CoasterService coasterService, UserCoasterService userCoasterService, RewardService rewardService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, Util util) {
        this.userService = userService;
        this.s3Uploader = s3Uploader;
        this.coasterService = coasterService;
        this.userCoasterService = userCoasterService;
        this.rewardService = rewardService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.util = util;
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "유저 닉네임 수정", notes="유저의 닉네임을 수정합니다.")
    public ResponseEntity modifiedUser(@RequestParam("userKey") String userKey, @RequestBody RequestUserDto.ModifyNickname requestUserDto){
        User user = userService.getUser(userKey);
        String newNickname = requestUserDto.getNickname();

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
    public ResponseEntity modifiedUserGoal(@RequestParam("userKey") String userKey, @RequestBody RequestUserDto.ModifyGoal requestUserDto){
        User user = userService.getUser(userKey);
        int newGoal = requestUserDto.getGoal();

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
    public ResponseEntity getUser(@RequestParam("userKey") String userKey, @RequestBody RequestCoasterDto requestCoasterDto){
        User user = userService.getUser(userKey);
        Coaster coaster = coasterService.getCoaster(requestCoasterDto.getCoasterKey());

        UserCoaster userCoaster = UserCoaster.builder()
                .coaster(coaster)
                .user(user)
                .build();

        // 유저 코스터 등록
        userCoasterService.addUserCoaster(userCoaster);

        // 코스터 등록에 따른 해금 확인
        rewardService.checkCoasterUnlock(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "유저 로그인", notes="유저 로그인 처리를 합니다.")
    public ResponseEntity login(@RequestParam("socialKey") String socialKey){
        System.out.println("login 실행되었다!");
        //처음으로 로그인 요청을 한 유저라면!
        if(!userService.friendCodeCheck(socialKey)){
            System.out.println("그럼 여기?");

            /**
             * 새로운 계정을 생성해줍니다.
             */
            //새로운 userKey를 생성하고 DB에 존재하는지 확인합니다. 이미 존재한다면 새로 생성해줍니다.
            String newKey = util.createKey(15);
            while(userService.userKeyCheck(newKey)){
                newKey = util.createKey(15);
            }

            //새로운 FriendCode를 생성하고 DB에 존재하는지 확인합니다. 이미 존재한다면 새로 생성을 반복합니다.
            String newFriendCode = util.createKey(10);
            while(userService.friendCodeCheck(newKey)){
                newFriendCode = util.createKey(10);
            }

            //새로운 유저 객체를 만들어줍니다.
            User createUser = User.builder()
                    .userKey(newKey)
                    .nickname("동밍")
                    .goal(0)
                    .friendCode(newFriendCode)
                    .socialKey(socialKey)
                    .socialType(1)
                    .imgUrl("https://your-habitat.s3.ap-northeast-2.amazonaws.com/static/default.png")
                    .build();

            //생성한 유저 입력
            userService.addUser(createUser);
            User user = userService.getUser(newKey);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserKey(), null, AuthorityUtils.createAuthorityList("user"));
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            TokenInfo tokenInfo = tokenProvider.createToken(authentication);
            user.setRefreshKey(tokenInfo.getRefreshToken());

            return new ResponseEntity(tokenInfo.getAccessToken(), HttpStatus.OK);

        } else {
            System.out.println("else");
            User getUser = userService.getBySocialKey(socialKey);
            System.out.println("GetUser");
            //여기까지 성공
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(getUser.getUserKey(), null, AuthorityUtils.createAuthorityList("user"));
            System.out.println("authenticationToken >> 여기는?" + authenticationToken);
            System.out.println("authenticationToken >> 여기는?" + authenticationToken.isAuthenticated());
            System.out.println("authenticationToken >> 여기는?" + authenticationToken.getName());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            System.out.println("authentication");
            TokenInfo tokenInfo = tokenProvider.createToken(authentication);
            System.out.println("CreateToken");
            getUser.setRefreshKey(tokenInfo.getRefreshToken());
            userService.addUser(getUser);
            System.out.println("new Token save");
            return new ResponseEntity(tokenInfo.getAccessToken(), HttpStatus.OK);
        }
    }

//    @PostMapping("/refresh")
//    public ResponseEntity validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){
//
//        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));
//
//        if(map.get("status").equals("402")){
//            log.info("RefreshController - Refresh Token이 만료.");
//            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//            return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
//        }
//
//        log.info("RefreshController - Refresh Token이 유효.");
//        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//        return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.OK);
//    }
}
