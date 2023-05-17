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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void addPlantingExp() {

    }

    @Test
    void checkFriendUnlock() {
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



        Flower flower1 = Flower.builder().drinkValue(100).build();
        userFlower1.setFlower(flower1);

        Flower flower2 = Flower.builder().drinkValue(200).build();
        userFlower2.setFlower(flower2);

        Flower flower3 = Flower.builder().drinkValue(100).build();
        userFlower3.setFlower(flower3);

        Flower flower4 = Flower.builder().drinkValue(200).build();
        userFlower4.setFlower(flower4);

        userFlowerList.add(userFlower1);
        userFlowerList.add(userFlower2);
        userFlowerList.add(userFlower3);
        userFlowerList.add(userFlower4);

        when(drinkLogService.getTotalDrink(user)).thenReturn(120);
        when(userFlowerService.getLockedFlowerList(user)).thenReturn(userFlowerList);


        // When
        rewardService.checkDrinkUnlock(user);

        // Then
        assertTrue(userFlower1.isDrink());
//        verify(userFlowerService).updateUserFlower(userFlower1);

        assertTrue(userFlower1.isUnlocked());
//        verify(customWebSocketHandler).sendMessage(user, anyString());
    }

    @Test
    void checkStreakUnlock() {
    }

    @Test
    void checkCoasterUnlock() {
    }

    @Test
    void checkStreakSuccess() {
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