package com.ssafy.habitat.service;

import com.ssafy.habitat.dto.ResponseFlowerDto;
import com.ssafy.habitat.entity.*;
import com.ssafy.habitat.websocket.CustomWebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RewardServiceTest {
    @Mock
    private FriendService friendService;

    @Mock
    private DrinkLogService drinkLogService;

    @Mock
    private CollectionService collectionService;

    @Mock
    private StreakLogService streakLogService;

    @Mock
    private FlowerService flowerService;

    @Mock
    private UserFlowerService userFlowerService;

    @Mock
    private UserFlowerLogService userFlowerLogService;

    @Mock
    private PlantingService plantingService;

    @Mock
    private CustomWebSocketHandler customWebSocketHandler;

    @InjectMocks
    private RewardService rewardService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("경험치 증가 테스트 - 수확X 레벨업X")
    public void addPlantingExp_get_X_levelUp_X() throws IOException {
        // Given
        User user = new User();
        int flowerCnt = 1;
        Flower curFlower = Flower.builder().maxExp(700).build();
        Planting curPlanting = Planting.builder().lv(5).exp(500).flower(curFlower).build();
        DrinkLog drinkLog = DrinkLog.builder().drink(500).build();

        when(collectionService.getCollectionCnt(user)).thenReturn(flowerCnt);
        when(plantingService.getCurrentPlant(user, flowerCnt)).thenReturn(curPlanting);

        // When
        rewardService.addPlantingExp(user, drinkLog);

    }

    @Test
    @DisplayName("경험치 증가 테스트 - 수확X 레벨업O")
    public void addPlantingExp_get_X_levelUp_O() throws IOException {
        // Given
        User user = new User();
        int flowerCnt = 1;
        Flower curFlower = Flower.builder().maxExp(700).build();
        Planting curPlanting = Planting.builder().lv(5).exp(550).flower(curFlower).build();
        DrinkLog drinkLog = DrinkLog.builder().drink(500).build();

        when(collectionService.getCollectionCnt(user)).thenReturn(flowerCnt);
        when(plantingService.getCurrentPlant(user, flowerCnt)).thenReturn(curPlanting);

        // When
        rewardService.addPlantingExp(user, drinkLog);
    }

    @Test
    @DisplayName("경험치 증가 테스트 - 수확O")
    public void addPlantingExp_get_O() throws IOException {
        // Given
        User user = new User();
        int flowerCnt = 1;
        Flower curFlower = Flower.builder().maxExp(700).build();
        Planting curPlanting = Planting.builder().lv(6).exp(650).flower(curFlower).build();
        DrinkLog drinkLog = DrinkLog.builder().drink(500).build();

        List<UserFlower> unlockedFlowerList = new ArrayList<>();
        UserFlower userFlower = UserFlower.builder().flower(curFlower).build();
        unlockedFlowerList.add(userFlower);

        when(collectionService.getCollectionCnt(user)).thenReturn(flowerCnt);
        when(plantingService.getCurrentPlant(user, flowerCnt)).thenReturn(curPlanting);
        when(userFlowerService.getUnlockedFlowerList(user)).thenReturn(unlockedFlowerList);

        // When
        rewardService.addPlantingExp(user, drinkLog);

    }

    @Test
    @DisplayName("친구 수 달성 해금 테스트")
    void checkFriendUnlock() throws IOException {
        // Given
        User user = new User();
        List<UserFlower> userFlowerList = new ArrayList<>();
        UserFlower userFlower1 = UserFlower.builder().friend(false).connect(true).drink(true).streak(true).build();
        UserFlower userFlower2 = UserFlower.builder().friend(true).connect(true).drink(true).streak(true).build();
        UserFlower userFlower3 = UserFlower.builder().friend(true).connect(true).drink(true).streak(true).build();
        UserFlower userFlower4 = UserFlower.builder().friend(false).connect(false).drink(true).streak(true).build();
        UserFlower userFlower5 = UserFlower.builder().friend(false).connect(false).drink(true).streak(true).build();

        Flower flower1 = Flower.builder().friendValue(10).build();
        userFlower1.setFlower(flower1);

        Flower flower2 = Flower.builder().friendValue(20).build();
        userFlower2.setFlower(flower2);

        Flower flower3 = Flower.builder().friendValue(10).build();
        userFlower3.setFlower(flower3);

        Flower flower4 = Flower.builder().friendValue(20).build();
        userFlower4.setFlower(flower4);

        Flower flower5 = Flower.builder().friendValue(10).build();
        userFlower5.setFlower(flower5);

        userFlowerList.add(userFlower1);
        userFlowerList.add(userFlower2);
        userFlowerList.add(userFlower3);
        userFlowerList.add(userFlower4);
        userFlowerList.add(userFlower5);


        when(friendService.getFriendCnt(user)).thenReturn(15);
        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);


        // When
        rewardService.checkFriendUnlock(user);

        // Then
    }

    @Test
    @DisplayName("누적음수량 달성 해금 테스트")
    void checkDrinkUnlock() throws IOException {
        // Given
        User user = new User();
        List<UserFlower> userFlowerList = new ArrayList<>();
        UserFlower userFlower1 = UserFlower.builder().drink(false).connect(true).streak(true).friend(true).build();
        UserFlower userFlower2 = UserFlower.builder().drink(true).connect(true).streak(true).friend(true).build();
        UserFlower userFlower3 = UserFlower.builder().drink(true).connect(true).streak(true).friend(true).build();
        UserFlower userFlower4 = UserFlower.builder().drink(false).connect(false).streak(true).friend(true).build();
        UserFlower userFlower5 = UserFlower.builder().drink(false).connect(false).streak(true).friend(true).build();

        Flower flower1 = Flower.builder().drinkValue(100).build();
        userFlower1.setFlower(flower1);

        Flower flower2 = Flower.builder().drinkValue(200).build();
        userFlower2.setFlower(flower2);

        Flower flower3 = Flower.builder().drinkValue(100).build();
        userFlower3.setFlower(flower3);

        Flower flower4 = Flower.builder().drinkValue(200).build();
        userFlower4.setFlower(flower4);

        Flower flower5 = Flower.builder().drinkValue(100).build();
        userFlower5.setFlower(flower5);

        userFlowerList.add(userFlower1);
        userFlowerList.add(userFlower2);
        userFlowerList.add(userFlower3);
        userFlowerList.add(userFlower4);
        userFlowerList.add(userFlower5);

        when(drinkLogService.getTotalDrink(user)).thenReturn(120);
        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);


        // When
        rewardService.checkDrinkUnlock(user);

        // Then
        assertTrue(userFlower1.isDrink());
        assertTrue(userFlower1.isUnlocked());
    }

    @Test
    @DisplayName("스트릭 달성 해금 테스트")
    void checkStreakUnlock() throws IOException {
        // Given
        User user = new User();
        List<UserFlower> userFlowerList = new ArrayList<>();
        UserFlower userFlower1 = UserFlower.builder().streak(false).connect(true).drink(true).friend(true).build();
        UserFlower userFlower2 = UserFlower.builder().streak(true).connect(true).drink(true).friend(true).build();
        UserFlower userFlower3 = UserFlower.builder().streak(true).connect(true).drink(true).friend(true).build();
        UserFlower userFlower4 = UserFlower.builder().streak(false).connect(false).drink(true).friend(true).build();
        UserFlower userFlower5 = UserFlower.builder().streak(false).connect(false).drink(true).friend(true).build();

        Flower flower1 = Flower.builder().streakValue(10).build();
        userFlower1.setFlower(flower1);

        Flower flower2 = Flower.builder().streakValue(20).build();
        userFlower2.setFlower(flower2);

        Flower flower3 = Flower.builder().streakValue(10).build();
        userFlower3.setFlower(flower3);

        Flower flower4 = Flower.builder().streakValue(20).build();
        userFlower4.setFlower(flower4);

        Flower flower5 = Flower.builder().streakValue(10).build();
        userFlower5.setFlower(flower5);

        userFlowerList.add(userFlower1);
        userFlowerList.add(userFlower2);
        userFlowerList.add(userFlower3);
        userFlowerList.add(userFlower4);
        userFlowerList.add(userFlower5);

        StreakLog streakLog = StreakLog.builder().curStreak(15).build();

        when(streakLogService.getDailyStreakLog(user)).thenReturn(streakLog);
        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);


        // When
        rewardService.checkStreakUnlock(user);

        // Then
    }

    @Test
    @DisplayName("코스터 연결 해금 테스트")
    void checkCoasterUnlock() throws IOException {
        // Given
        User user = new User();
        List<UserFlower> userFlowerList = new ArrayList<>();
        UserFlower userFlower1 = UserFlower.builder().drink(true).connect(false).streak(true).friend(true).build();
        UserFlower userFlower2 = UserFlower.builder().drink(true).connect(true).streak(true).friend(true).build();
        UserFlower userFlower3 = UserFlower.builder().drink(true).connect(false).streak(false).friend(true).build();

        Flower flower1 = Flower.builder().drinkValue(100).build();
        userFlower1.setFlower(flower1);

        Flower flower2 = Flower.builder().drinkValue(200).build();
        userFlower2.setFlower(flower2);

        Flower flower3 = Flower.builder().drinkValue(100).build();
        userFlower3.setFlower(flower3);

        userFlowerList.add(userFlower1);
        userFlowerList.add(userFlower2);
        userFlowerList.add(userFlower3);

        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);

        // When
        rewardService.checkCoasterUnlock(user);

        // Then
        assertTrue(userFlower1.isDrink());
        assertTrue(userFlower1.isUnlocked());
    }

    @Test
    @DisplayName("스트릭 달성에 따른 달성여부 확인 테스트(스트릭 없고 목표 달성)")
    void checkStreakSuccess_Success() throws IOException {
        // Given
        int totalDailyDrink = 1000;
        StreakLog todayStreakLog = null;
        User user = User.builder().goal(800).build();
        when(drinkLogService.getDailyTotalDrink(user)).thenReturn(totalDailyDrink);
        when(streakLogService.getDailyStreakLog(user)).thenReturn(todayStreakLog);

        // When
        boolean result = rewardService.checkStreakSuccess(user);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("스트릭 달성에 따른 달성여부 확인 테스트(스트릭 있고 목표 달성)")
    void checkStreakSuccess_Fail_AlreadyStreakExist() throws IOException {
        // Given
        int totalDailyDrink = 1000;
        StreakLog todayStreakLog = StreakLog.builder().build();
        User user = User.builder().goal(800).build();
        when(drinkLogService.getDailyTotalDrink(user)).thenReturn(totalDailyDrink);
        when(streakLogService.getDailyStreakLog(user)).thenReturn(todayStreakLog);

        // When
        boolean result = rewardService.checkStreakSuccess(user);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("스트릭 달성에 따른 달성여부 확인 테스트(스트릭 없고 목표 미달성)")
    void checkStreakSuccess_Fail_GoalUnder() throws IOException {
        // Given
        int totalDailyDrink = 1000;
        StreakLog todayStreakLog = null;
        User user = User.builder().goal(1500).build();
        when(drinkLogService.getDailyTotalDrink(user)).thenReturn(totalDailyDrink);
        when(streakLogService.getDailyStreakLog(user)).thenReturn(todayStreakLog);

        // When
        boolean result = rewardService.checkStreakSuccess(user);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("스트릭 달성에 따른 달성여부 확인 테스트(스트릭 있고 목표 미달성)")
    void checkStreakSuccess_Fail_AlreadyStreakExist_GoalUnder() throws IOException {
        // Given
        int totalDailyDrink = 1000;
        StreakLog todayStreakLog = StreakLog.builder().build();
        User user = User.builder().goal(1500).build();
        when(drinkLogService.getDailyTotalDrink(user)).thenReturn(totalDailyDrink);
        when(streakLogService.getDailyStreakLog(user)).thenReturn(todayStreakLog);

        // When
        boolean result = rewardService.checkStreakSuccess(user);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("모든 조건 달성 여부 확인 테스트(모두 달성한 경우)")
    void isFullyUnlocked_AllUnlocked () {
        // Given
        UserFlower userFlower = UserFlower.builder().friend(true).drink(true).streak(true).connect(true).build();

        // When
        rewardService.isFullyUnlocked(userFlower);

        // Then
    }

    @Test
    @DisplayName("모든 조건 달성 여부 확인 테스트(아직 달성 못한 경우 - friend)")
    void isFullyUnlocked_PartialyUnlocked_friend () {
        // Given
        UserFlower userFlower = UserFlower.builder().friend(false).drink(true).streak(true).connect(true).build();

        // When
        rewardService.isFullyUnlocked(userFlower);

        // Then
    }

    @Test
    @DisplayName("모든 조건 달성 여부 확인 테스트(아직 달성 못한 경우 - drink)")
    void isFullyUnlocked_PartialyUnlocked_drink () {
        // Given
        UserFlower userFlower = UserFlower.builder().friend(true).drink(false).streak(true).connect(true).build();

        // When
        rewardService.isFullyUnlocked(userFlower);

        // Then
    }

    @Test
    @DisplayName("모든 조건 달성 여부 확인 테스트(아직 달성 못한 경우 - streak)")
    void isFullyUnlocked_PartialyUnlocked_streak () {
        // Given
        UserFlower userFlower = UserFlower.builder().friend(true).drink(true).streak(false).connect(true).build();

        // When
        rewardService.isFullyUnlocked(userFlower);

        // Then
    }

    @Test
    @DisplayName("모든 조건 달성 여부 확인 테스트(아직 달성 못한 경우 - connect)")
    void isFullyUnlocked_PartialyUnlocked_connect () {
        // Given
        UserFlower userFlower = UserFlower.builder().friend(true).drink(true).streak(true).connect(false).build();

        // When
        rewardService.isFullyUnlocked(userFlower);

        // Then
    }

    @Test
    @DisplayName("꽃 완전 해금 테스트")
    void unlockUserFlower_UnlockUserFlower() {
        // Given
        User user = new User();
        Flower flower = new Flower();
        UserFlower userFlower = new UserFlower();

        // When
        rewardService.unlockUserFlower(user, flower, userFlower);

        // Then
        verify(userFlowerService, times(1)).unlockUserFlower(userFlower);
    }

    @Test
    @DisplayName("유저의 컬렉션 상태 목록 테스트")
    void getCollection_ReturnUserCollectionStateList(){
        // Given
        User user = new User();
        Flower flower1 = new Flower();
        Flower flower2 = new Flower();
        flower2.setFlowerKey(2);
        Flower flower3 = new Flower();
        flower3.setFlowerKey(3);

        Collection collection = new Collection();
        collection.setFlower(flower1);
        UserFlower userFlower = new UserFlower();
        userFlower.setUser(user);
        userFlower.setFlower(flower1);

        List<Flower> flowerList = new ArrayList<>();
        flowerList.add(flower1);
        flowerList.add(flower2);
        flowerList.add(flower3);

        List<Collection> collectionList = new ArrayList<>();
        collectionList.add(collection);

        HashSet<Integer> collectionHashSet = new HashSet<>();
        collectionHashSet.add(2);

        List<UserFlower> unlockedUserFlowerList = new ArrayList<>();
        unlockedUserFlowerList.add(userFlower);

        HashSet<Integer> unlockedUserFlowerHashSet = new HashSet<>();
        unlockedUserFlowerHashSet.add(3);

        when(flowerService.getFlowerList()).thenReturn(flowerList).thenReturn(flowerList);
        when(collectionService.getCollectionList(user)).thenReturn(collectionList);
        when(userFlowerService.getUnlockedFlowerList(user)).thenReturn(unlockedUserFlowerList);

        List<ResponseFlowerDto.Collection> responseFlowerDtoList = new ArrayList<>();

        // When
        rewardService.getCollection(user);
        List<ResponseFlowerDto.Collection> result = new ArrayList<>();

        // Then
        assertEquals(responseFlowerDtoList, result);
    }
}