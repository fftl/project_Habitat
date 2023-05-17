//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.DrinkLog;
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.exception.CustomException;
//import com.ssafy.habitat.exception.ErrorCode;
//import com.ssafy.habitat.repository.DrinkLogRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class DrinkLogServiceTest {
//
//    @Mock
//    private DrinkLogRepository drinkLogRepository;
//
//    @InjectMocks
//    private DrinkLogService drinkLogService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("최근 로그 조회 테스트")
//    public void getRecentLog_ReturnRecentDrinkLog() {
//        // Given
//        User user = new User();
//        DrinkLog recentLog = new DrinkLog();
//        recentLog.setCreatedAt(LocalDateTime.now());
//        when(drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.of(recentLog));
//
//        // When
//        LocalDateTime result = drinkLogService.getRecentLog(user);
//
//        // Then
//        assertEquals(recentLog.getCreatedAt(), result);
//    }
//
//    @Test
//    @DisplayName("최근 로그 조회 테스트(없을 경우)")
//    public void getRecentLog_WhenRecentDrinkLogNotExist_ReturnNull() {
//        // Given
//        User user = new User();
//        when(drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(user)).thenReturn(Optional.empty());
//
//        // When
//        LocalDateTime result = drinkLogService.getRecentLog(user);
//
//        // Then
//        assertEquals(null, result);
//    }
//
//
//
//    @Test
//    @DisplayName("로그 상세 조회 테스트")
//    public void getLog_ReturnDrinkLog() {
//        // Given
//        int drinkLogKey = 1;
//        DrinkLog drinkLog = new DrinkLog();
//        when(drinkLogRepository.findById(drinkLogKey)).thenReturn(Optional.of(drinkLog));
//
//        // When
//        DrinkLog result = drinkLogService.getLog(drinkLogKey);
//
//        // Then
//        assertEquals(drinkLog, result);
//    }
//
//    @Test
//    @DisplayName("로그 상세 조회 테스트")
//    public void getLog_WhenDrinkLogNotExist_ThrowsException() {
//        // Given
//        int drinkLogKey = 1;
//        DrinkLog drinkLog = new DrinkLog();
//        drinkLog.setDrinkLogKey(2);
//        when(drinkLogRepository.findById(drinkLogKey)).thenReturn(Optional.empty());
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            drinkLogService.getLog(drinkLogKey);
//        });
//
//        assertEquals(ErrorCode.DRINK_LOG_NOT_FOUND, exception.getErrorCode());
//    }
//
//
//    @Test
//    @DisplayName("모든 로그 조회 테스트")
//    public void getAllLogs_ReturnDrinkLogList() {
//        // Given
//        User user = new User();
//        List<DrinkLog> drinkLogList = new ArrayList<>();
//        drinkLogList.add(new DrinkLog());
//        drinkLogList.add(new DrinkLog());
//        when(drinkLogRepository.findAllByUserAndIsRemoved(user, false)).thenReturn(drinkLogList);
//
//        // When
//        List<DrinkLog> result = drinkLogService.getAllLogs(user);
//
//        // Then
//        assertEquals(drinkLogList, result);
//    }
//
//
//    @Test
//    @DisplayName("일별 로그 조회 테스트")
//    public void getDailyLogs_ReturnDailyDrinkLogList() {
//        // Given
//        User user = new User();
//        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
//        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
//        List<DrinkLog> drinkLogList = new ArrayList<>();
//        drinkLogList.add(new DrinkLog());
//        drinkLogList.add(new DrinkLog());
//        when(drinkLogRepository.findAllByUserAndAndIsRemovedAndModifiedAtBetween(user, false, startDatetime, endDatetime)).thenReturn(drinkLogList);
//
//        // When
//        List<DrinkLog> result = drinkLogService.getDailyLogs(user);
//
//        // Then
//        assertEquals(drinkLogList, result);
//    }
//
//
//    @Test
//    @DisplayName("음료 섭취 로그 추가")
//    public void addDrinkLog_SaveNewDrinkLog() {
//        // Given
//        DrinkLog newDrinkLog = new DrinkLog();
//
//        // When
//        drinkLogService.addDrinkLog(newDrinkLog);
//
//        // Then
//        verify(drinkLogRepository).save(newDrinkLog);
//    }
//
//    @Test
//    @DisplayName("일별 총 음료 섭취량 계산 테스트")
//    public void getDailyTotalDrink_ReturnDailyTotalDrink() {
//        // Given
//        User user = new User();
//        List<DrinkLog> dailyDrinkLogList = new ArrayList<>();
//        DrinkLog drinkLog1 = DrinkLog.builder().drinkLogKey(1).drink(100).build();
//        DrinkLog drinkLog2 = DrinkLog.builder().drinkLogKey(2).drink(200).build();
//
//        dailyDrinkLogList.add(drinkLog1);
//        dailyDrinkLogList.add(drinkLog2);
//        when(drinkLogService.getDailyLogs(user)).thenReturn(dailyDrinkLogList);
//
//        // When
//        int result = drinkLogService.getDailyTotalDrink(user);
//
//        // Then
//        int expectedTotal = 0;
//        for (DrinkLog drinkLog : dailyDrinkLogList) {
//            expectedTotal += drinkLog.getDrink();
//        }
//        assertEquals(expectedTotal, result);
//    }
//
//
//    @Test
//    @DisplayName("전체 음료 섭취량 계산 테스트")
//    public void getTotalDrink_ReturnTotalDrink() {
//        // Given
//        User user = new User();
//        List<DrinkLog> drinkLogList = new ArrayList<>();
//
//        DrinkLog drinkLog1 = DrinkLog.builder().drinkLogKey(1).drink(100).build();
//        DrinkLog drinkLog2 = DrinkLog.builder().drinkLogKey(2).drink(200).build();
//
//        drinkLogList.add(drinkLog1);
//        drinkLogList.add(drinkLog2);
//
//        when(drinkLogService.getAllLogs(user)).thenReturn(drinkLogList);
//
//        // When
//        int result = drinkLogService.getTotalDrink(user);
//
//        // Then
//        int expectedTotal = 0;
//        for (DrinkLog drinkLog : drinkLogList) {
//            expectedTotal += drinkLog.getDrink();
//        }
//        assertEquals(expectedTotal, result);
//    }
//
//
//
//}