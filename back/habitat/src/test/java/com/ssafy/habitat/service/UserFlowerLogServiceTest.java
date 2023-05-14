package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.UserFlowerLog;
import com.ssafy.habitat.repository.UserFlowerLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserFlowerLogServiceTest {

    @Mock
    UserFlowerLogRepository userFlowerLogRepository;
    @InjectMocks
    UserFlowerLogService userFlowerLogService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("새로운 해금 기록 등록 테스트")
    void addUserFlowerLog_SaveNewUserFlowerLog() {
        // Given
        UserFlowerLog newUserFlowerLog = new UserFlowerLog();
        when(userFlowerLogRepository.save(newUserFlowerLog)).thenReturn(null);

        // When
        userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

        // Then
        verify(userFlowerLogRepository, times(1)).save(newUserFlowerLog);
    }
}