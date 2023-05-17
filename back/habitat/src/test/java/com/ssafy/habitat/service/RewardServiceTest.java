//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.Flower;
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.entity.UserFlower;
//import com.ssafy.habitat.websocket.CustomWebSocketHandler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//class RewardServiceTest {
//    @Mock
//    private FriendService friendService;
//
//    @Mock
//    private DrinkLogService drinkLogService;
//
//    @Mock
//    private CollectionService collectionService;
//
//    @Mock
//    private StreakLogService streakLogService;
//
//    @Mock
//    private FlowerService flowerService;
//
//    @Mock
//    private UserFlowerService userFlowerService;
//
//    @Mock
//    private UserFlowerLogService userFlowerLogService;
//
//    @Mock
//    private PlantingService plantingService;
//
//    @Mock
//    private CustomWebSocketHandler customWebSocketHandler;
//
//    @InjectMocks
//    private RewardService rewardService;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void addPlantingExp() {
//
//    }
//
//    @Test
//    void checkFriendUnlock() {
//    }
//
//    @Test
//    @DisplayName("누적음수량 달성 해금 테스트")
//    void checkDrinkUnlock() throws IOException {
//        // Given
//        User user = new User();
//        List<UserFlower> userFlowerList = new ArrayList<>();
//        UserFlower userFlower = new UserFlower();
//        userFlower.setDrink(false);
//        userFlower.setConnect(true);
//        userFlower.setStreak(true);
//        userFlower.setFriend(true);
//        Flower flower = new Flower();
//        flower.setDrinkValue(100);
//        userFlower.setFlower(flower);
//        userFlowerList.add(userFlower);
//        when(drinkLogService.getTotalDrink(user)).thenReturn(120);
//
//        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);
//
//
//        // When
//        rewardService.checkDrinkUnlock(user);
//
//        // Then
//        assertTrue(userFlower.isDrink());
////        verify(userFlowerService).addUserFlower(userFlower);
//
////        verify(userFlowerLogService).addUserFlowerLog(any(UserFlowerLog.class));
//
//        assertTrue(userFlower.isUnlocked());
////        verify(customWebSocketHandler).sendMessage(user, anyString());
//    }
//
//    @Test
//    void checkStreakUnlock() {
//    }
//
//    @Test
//    void checkCoasterUnlock() {
//    }
//
//    @Test
//    void checkStreakSuccess() {
//    }
//}