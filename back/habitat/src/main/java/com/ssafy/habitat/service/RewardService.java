package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {
    private FriendService friendService;
    private DrinkLogService drinkLogService;
    private CollectionService collectionService;
    private AvailableFlowerService availableFlowerService;
    private StreakLogService streakLogService;
    private FlowerService flowerService;
    private UserFlowerService userFlowerService;
    private UserFlowerLogService userFlowerLogService;

    @Autowired
    public RewardService(FriendService friendService, DrinkLogService drinkLogService, CollectionService collectionService, AvailableFlowerService availableFlowerService, StreakLogService streakLogService, FlowerService flowerService, UserFlowerService userFlowerService, UserFlowerLogService userFlowerLogService) {
        this.friendService = friendService;
        this.drinkLogService = drinkLogService;
        this.collectionService = collectionService;
        this.availableFlowerService = availableFlowerService;
        this.streakLogService = streakLogService;
        this.flowerService = flowerService;
        this.userFlowerService = userFlowerService;
        this.userFlowerLogService = userFlowerLogService;
    }

    public void checkFriendUnlock(User user) {
        // 친구 등록 수에 따른 달성여부 확인

        int friendCnt = user.getFriendList().size(); // 친구변화에 따른 현재 친구수
        List<UserFlower> userFlowerList = userFlowerService.getLockedFlowerList(user); // 해당 유저의 해금안된 꽃 리스트

        for (int i = 0; i < userFlowerList.size(); i++) { // 아직 해금 되지 않은 꽃들에 대하여
            UserFlower userFlower = userFlowerList.get(i);
            Flower flower = userFlower.getFlower();

            // 이전에는 친구 조건을 달성하지 못했었고, 현재 친구 조건을 달성했다면
            if(userFlower.isFriend() == false && friendCnt >= flower.getFriendValue()) {
                // 해당 꽃의 친구조건 해금
                userFlower.setFriend(true);
                userFlowerService.addUserFlower(userFlower);

                // 해금 기록 저장
                UserFlowerLog newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('f').build();
                userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                // 다른 조건들까지 모두 해금되었다면
                if(isFullyUnlocked(userFlower)) {
                    // 완전 해금
                    userFlower.setUnlocked(true);
                    userFlowerService.addUserFlower(userFlower);

                    // 완전 해금 기록 저장
                    newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('t').build();
                    userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 친구 수 " + friendCnt+"명 달성으로 " + flower.getName() + " 꽃이 해금되었습니다!!!");
                }
            }
        }
    }

    public void checkDrinkUnlock(User user) {
        // 현재 음수량에 따른 달성여부 확인

        int totalDrink = drinkLogService.getTotalDrink(user); // 음수량 변화에 따른 현재 누적음수량
        List<UserFlower> userFlowerList = userFlowerService.getLockedFlowerList(user); // 해당 유저의 해금안된 꽃 리스트

        for (int i = 0; i < userFlowerList.size(); i++) { // 아직 해금 되지 않은 꽃들에 대하여
            UserFlower userFlower = userFlowerList.get(i);
            Flower flower = userFlower.getFlower();

            // 이전에는 누적음수량 조건을 달성하지 못했었고, 현재 누적음수량 조건을 달성했다면
            if(userFlower.isDrink() == false && totalDrink >= flower.getDrinkValue()) {
                // 해당 꽃의 누적음수량 조건 해금
                userFlower.setDrink(true);
                userFlowerService.addUserFlower(userFlower);

                // 해금 기록 저장
                UserFlowerLog newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('d').build();
                userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                // 다른 조건들까지 모두 해금되었다면
                if(isFullyUnlocked(userFlower)) {
                    // 완전 해금
                    userFlower.setUnlocked(true);
                    userFlowerService.addUserFlower(userFlower);

                    // 완전 해금 기록 저장
                    newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('t').build();
                    userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 누적음수량 "+ flower.getDrinkValue()+"ml 달성으로 " + flower.getName() + " 꽃이 해금되었습니다!!!");
                }
            }
        }

    }

    public boolean isFullyUnlocked(UserFlower userFlower) {
        // 모든 조건을 달성했다면 true 리턴
        if(userFlower.isFriend() && userFlower.isDrink() && userFlower.isStreak() && userFlower.isConnect()) {
            return true;
        }
        return false;
    }
}
