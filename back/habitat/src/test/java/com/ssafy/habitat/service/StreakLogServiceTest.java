package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.StreakLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.StreakLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StreakLogServiceTest {
    @Mock
    StreakLogRepository streakLogRepository;
    @InjectMocks
    StreakLogService streakLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("스프린트 로그 추가 테스트 (최근 streak 없을 경우) - 새로 시작")
    void addStreakLog_WhenNoRecentStreak_SaveNewStreakLog() {
        // Given
        User user = new User();
        user.setStreakLogList(new ArrayList<>());

        // When
        streakLogService.addStreakLog(user);

        // Then
        verify(streakLogRepository, times(1)).save(any(StreakLog.class));
    }

    @Test
    @DisplayName("스프린트 로그 추가 테스트 (가장 최근 streak의 날짜가 오늘일 경우) - 업데이트 X")
    void addStreakLog_WhenRecentStreakToday_NotSaveNewStreakLog() {
        // Given
        User user = new User();
        List<StreakLog> streakLogList = new ArrayList<>();
        streakLogList.add(createStreakLog(LocalDate.now()));
        user.setStreakLogList(streakLogList);

        // When
        streakLogService.addStreakLog(user);

        // Then
        verify(streakLogRepository, never()).save(any(StreakLog.class));
    }

    @Test
    @DisplayName("스프린트 로그 추가 테스트 (가장 최근 streak의 날짜가 어제일 경우) - 연속 업데이트 O")
    void addStreakLog_WhenRecentStreakYesterday_SaveNewStreakLogWithIncreasedStreak() {
        // Given
        User user = new User();
        List<StreakLog> streakLogList = new ArrayList<>();
        streakLogList.add(createStreakLog(LocalDate.now().minusDays(1)));
        user.setStreakLogList(streakLogList);

        // When
        streakLogService.addStreakLog(user);

        // Then
        verify(streakLogRepository, times(1)).save(any(StreakLog.class));
    }

    @Test
    @DisplayName("스프린트 로그 추가 테스트 (가장 최근 streak의 날짜가 어제 이전일 경우) - 새로 시작")
    void addStreakLog_WhenRecentStreakBeforeYesterday_SaveNewStreakLogWithResetStreak() {
        // Given
        User user = new User();
        List<StreakLog> streakLogList = new ArrayList<>();
        streakLogList.add(createStreakLog(LocalDate.now().minusDays(2)));
        user.setStreakLogList(streakLogList);

        // When
        streakLogService.addStreakLog(user);

        // Then
        verify(streakLogRepository, times(1)).save(any(StreakLog.class));
    }

    @Test
    @DisplayName("일일 스프린트 로그 조회 테스트")
    void getDailyStreakLog_ReturnDailyStreakLog() {
        // Given
        User user = new User();
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        when(streakLogRepository.findByUserAndCreatedAtBetween(user, startDatetime, endDatetime))
                .thenReturn(Optional.ofNullable(createStreakLog(LocalDate.now())));

        // When
        StreakLog result = streakLogService.getDailyStreakLog(user);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getCreatedAt().toLocalDate());
    }

    private StreakLog createStreakLog(LocalDate date) {
        StreakLog streakLog = new StreakLog();
        streakLog.setCreatedAt(LocalDateTime.of(date, LocalTime.now()));

        return streakLog;
    }


}