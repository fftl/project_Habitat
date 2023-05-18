package com.ssafy.habitat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.habitat.config.JwtFilter;
import com.ssafy.habitat.config.TokenProvider;
import com.ssafy.habitat.dto.RequestCoasterDto;
import com.ssafy.habitat.dto.RequestUserDto;
import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserCoaster;
import com.ssafy.habitat.service.*;
import com.ssafy.habitat.utils.S3Uploader;
import com.ssafy.habitat.utils.Util;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)}
        , excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private S3Uploader s3Uploader;
    @MockBean
    private CoasterService coasterService;
    @MockBean
    private UserCoasterService userCoasterService;
    @MockBean
    private RewardService rewardService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @MockBean
    private FlowerService flowerService;
    @MockBean
    private PlantingService plantingService;
    @MockBean
    private UserFlowerService userFlowerService;
    @MockBean
    private Util util;

    static User user;
    static Coaster coaster;
    static ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .nickname("주먹이 무서운 영국사람")
                .goal(1500)
                .build();

        coaster = Coaster.builder()
                .coasterKey("coasterKey")
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void modifiedUser() {
    }

    @Test
    void randomNickname() {
    }

    @Test
    @DisplayName("유저 목표 수정 테스트(양수 입력)")
    void modifiedUserGoalPlus() throws Exception {
        //given
        when(userService.getUser("1234")).thenReturn(user);
        when(tokenProvider.getUserKey(any(String.class))).thenReturn("1234");
        RequestUserDto.ModifyGoal request = RequestUserDto.ModifyGoal.builder()
                .goal(2000)
                .build();

        System.out.println("Before > " + user.getGoal());

        //when
        ResultActions result  = mockMvc.perform(
                        patch("/api/users/modify/goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer abcd")
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print());


        //then (실제로 변경이 되었는지 테스트를 할 방법이 있나?
        assertEquals(user.getGoal(), request.getGoal());
        verify(userService, times(1)).addUser(user);
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 목표 수정 테스트(음수 입력)")
    void modifiedUserGoalMinus() throws Exception {
        //given
        when(userService.getUser("1234")).thenReturn(user);
        when(tokenProvider.getUserKey(any(String.class))).thenReturn("1234");
        RequestUserDto.ModifyGoal request = RequestUserDto.ModifyGoal.builder()
                .goal(-100)
                .build();

        //when
        ResultActions result  = mockMvc.perform(
                        patch("/api/users/modify/goal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer abcd")
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        //then (실제로 변경이 되었는지 테스트를 할 방법이 있나?
        verify(userService, times(0)).addUser(user);
        result.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("-400005"));
    }

    @Test
    void modifiedUserImg() {
    }

    @Test
    @DisplayName("유저 조회")
    void getUser() throws Exception {
        when(userService.getUser("1234")).thenReturn(user);
        when(tokenProvider.getUserKey(any(String.class))).thenReturn("1234");

        ResultActions result = mockMvc.perform(get("/api/users").header("Authorization", "Bearer abcd")).andDo(print());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("주먹이 무서운 영국사람"));
    }

    @Test
    @DisplayName("코스터 등록")
    void addCoaster() throws Exception {
        when(userService.getUser("1234")).thenReturn(user);
        when(tokenProvider.getUserKey(any(String.class))).thenReturn("1234");
        when(coasterService.getCoaster(any(String.class))).thenReturn(coaster);
        RequestCoasterDto request = RequestCoasterDto
                .builder()
                .coasterKey("coasterKeyValue")
                .build();


        ResultActions result  = mockMvc.perform(
                post("/api/users/coaster")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer abcd")
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        verify(userCoasterService, times(1)).addUserCoaster(any(UserCoaster.class));
        verify(rewardService, times(1)).checkCoasterUnlock(any(User.class));

        result.andExpect(status().isOk());
    }

    @Test
    void login() {
    }

    @Test
    @DisplayName("리프래시 토큰 갱신하기")
    void validateRefreshToken() throws Exception {

        ResultActions result  = mockMvc.perform(
                        post("/api/users/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer abcd"))
                .andDo(print());

        result.andExpect(status().isOk());
    }
}