package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.AvailableFlower;
import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.entity.User;
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

    @Autowired
    public RewardService(FriendService friendService, DrinkLogService drinkLogService, CollectionService collectionService, AvailableFlowerService availableFlowerService, StreakLogService streakLogService, FlowerService flowerService) {
        this.friendService = friendService;
        this.drinkLogService = drinkLogService;
        this.collectionService = collectionService;
        this.availableFlowerService = availableFlowerService;
        this.streakLogService = streakLogService;
        this.flowerService = flowerService;
    }

    public void checkFriendAvailable(User user) {
        // 친구 등록 수에 따른 달성여부 확인

        int friendCnt = user.getFriendList().size(); // 친구변화에 따른 현재 친구수
        List<Flower> friendFlowerList = flowerService.getFriendFlowerList(); // 친구변화가 조건인 꽃 리스트

        for (int i = 0; i < friendFlowerList.size(); i++) {
            Flower curFlower = friendFlowerList.get(i);

            // 특정 꽃의 조건을 만족했다면
            if(friendCnt == curFlower.getFriendValue()) {
                AvailableFlower newAvailableFlower = AvailableFlower.builder()
                        .flower(curFlower)
                        .user(user)
                        .build();

                // 해금 성공(새로 등록됨)
                if(availableFlowerService.addAvailableFlower(newAvailableFlower)) {
                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 "+curFlower.getFlowerKey()+"번 꽃이 해금되었습니다!!!");
                }
            }
        }


    }
}
