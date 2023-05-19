package com.ssafy.habitat.controller;

import com.ssafy.habitat.config.JwtFilter;
import com.ssafy.habitat.config.TokenProvider;
import com.ssafy.habitat.entity.Collection;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.service.*;
import com.ssafy.habitat.utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureWebMvc
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {UserController.class},
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
//    @MockBean
//    private S3Uploader s3Uploader;
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

    /**
     * 참고1
     * https://blog.naver.com/PostView.naver?blogId=qjawnswkd&logNo=222392092171&parentCategoryNo=&categoryNo=41&viewDate=&isShowPopularPosts=false&from=postView
     *
     * */
//    private Collection<? extends GrantedAuthority> authorities(User user){
//        Collection<? extends GrantedAuthority> authorities = new ArrayList<E>();
//    }
    static String token = "Bearer ***REMOVED***";
    static User user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ArrayList<String> roles = new ArrayList<>();
        roles.add("USER");
        user = User.builder()
                .userKey("kFjy9WBXcxQMFpf")
                .goal(1500)
                .imgUrl("asdf")
                .nickname("주먹이 무서운 영국사람")
                .roles(roles)
                .build();
    }

    @Test
    void modifiedUser() {
    }

    @Test
    void modifiedUserGoal() {
    }

    @Test
    void modifiedUserImg() {
    }

    @Test
    void getUser() throws Exception {
        //given
        when(tokenProvider.getUserKey(any(String.class))).thenReturn("kFjy9WBXcxQMFpf");
        when(userService.getUser(any(String.class))).thenReturn(user);

        //when
        ResultActions result = mockMvc.perform(
                get("/api/users")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        result
                .andExpect((ResultMatcher) jsonPath("$.nickname",is("주먹이 무서운 영국사람")));

    }
    @Test
    void addCoaster() {
    }

    @Test
    void login() {
    }

    @Test
    void validateRefreshToken() {
    }
}