package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import com.ssafy.habitat.repository.UserFlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserFlowerServiceTest {
    @Mock
    UserFlowerRepository userFlowerRepository;
    @InjectMocks
    UserFlowerService userFlowerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("잠긴 꽃 목록 조회 테스트")
    void getLockedFlowerList_ReturnLockedFlowerList() {
        User user = new User();
        List<UserFlower> lockedFlowerList = new ArrayList<>();
        when(userFlowerRepository.findByUserAndIsUnlocked(user, false)).thenReturn(lockedFlowerList);

        userFlowerService.getLockedFlowerList(user);

        List<UserFlower> expectedLockedFlowerList = new ArrayList<>();
        assertEquals(expectedLockedFlowerList, lockedFlowerList);
    }

    @Test
    @DisplayName("0유저-꽃 관계 등록 테스트")
    void addUserFlower_SaveNewUserFlower() {
        UserFlower newUserFlower = new UserFlower();
        when(userFlowerRepository.save(newUserFlower)).thenReturn(null);

        userFlowerService.addUserFlower(newUserFlower);

    }

    @Test
    @DisplayName("해금된 꽃 목록 조회 테스트")
    void getUnlockedFlowerList_ReturnUnlockedFlowerList() {
        User user = new User();
        List<UserFlower> lockedFlowerList = new ArrayList<>();
        when(userFlowerRepository.findByUserAndIsUnlocked(user, true)).thenReturn(lockedFlowerList);

        userFlowerService.getUnlockedFlowerList(user);

        List<UserFlower> expectedLockedFlowerList = new ArrayList<>();
        assertEquals(expectedLockedFlowerList, lockedFlowerList);
    }
}