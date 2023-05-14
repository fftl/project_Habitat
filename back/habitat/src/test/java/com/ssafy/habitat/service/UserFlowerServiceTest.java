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
import static org.mockito.Mockito.*;

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
        // Given
        User user = new User();
        List<UserFlower> lockedFlowerList = new ArrayList<>();
        when(userFlowerRepository.findByUserAndIsUnlocked(user, false)).thenReturn(lockedFlowerList);

        // When
        List<UserFlower> result = userFlowerService.getLockedFlowerList(user);

        // Then
        assertEquals(lockedFlowerList, result);
        verify(userFlowerRepository, times(1)).findByUserAndIsUnlocked(user, false);
    }

    @Test
    @DisplayName("0유저-꽃 관계 등록 테스트")
    void addUserFlower_SaveNewUserFlower() {
        // Given
        UserFlower newUserFlower = new UserFlower();
        when(userFlowerRepository.save(newUserFlower)).thenReturn(null);

        // When
        userFlowerService.addUserFlower(newUserFlower);

        // Then
        verify(userFlowerRepository, times(1)).save(newUserFlower);

    }

    @Test
    @DisplayName("해금된 꽃 목록 조회 테스트")
    void getUnlockedFlowerList_ReturnUnlockedFlowerList() {
        // Given
        User user = new User();
        List<UserFlower> unlockedFlowerList = new ArrayList<>();
        when(userFlowerRepository.findByUserAndIsUnlocked(user, true)).thenReturn(unlockedFlowerList);

        // When
        List<UserFlower> result = userFlowerService.getUnlockedFlowerList(user);

        // Then
        assertEquals(unlockedFlowerList, result);
    }
}