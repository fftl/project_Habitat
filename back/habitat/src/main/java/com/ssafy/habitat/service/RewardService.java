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
    private StreakLogService streakLogService;
    private FlowerService flowerService;
    private UserFlowerService userFlowerService;
    private UserFlowerLogService userFlowerLogService;
    private PlantingService plantingService;

    @Autowired
    public RewardService(FriendService friendService, DrinkLogService drinkLogService, CollectionService collectionService, StreakLogService streakLogService, FlowerService flowerService, UserFlowerService userFlowerService, UserFlowerLogService userFlowerLogService, PlantingService plantingService) {
        this.friendService = friendService;
        this.drinkLogService = drinkLogService;
        this.collectionService = collectionService;
        this.streakLogService = streakLogService;
        this.flowerService = flowerService;
        this.userFlowerService = userFlowerService;
        this.userFlowerLogService = userFlowerLogService;
        this.plantingService = plantingService;
    }

    public void addPlantingExp(User user, DrinkLog drinkLog) {
        // 섭취량에 따른 경험치 증가
        Planting planting = plantingService.getCurrentPlant(user); // 현재 키우는 꽃

        int prevExp = planting.getExp();
        int max = planting.getFlower().getMaxExp();
        int prevLv = planting.getLv();

        // 경험치 증가
        int nextExp = prevExp + drinkLog.getDrink()/10; // 마신 만큼의 10% 경험치 적립
        int nextLv = (nextExp/100);

        // 수확 확인
        if(nextExp >= planting.getFlower().getMaxExp()) { // 최대 경험치 도달 -> 수확 이벤트 발생
            nextExp = planting.getFlower().getMaxExp(); // 최대 경험치로 수정
            nextLv = nextExp/100; // 최대 레벨로 수정

            // 컬렉션에 추가
            Collection newCollection = Collection.builder().user(user).flower(planting.getFlower()).build();
            collectionService.addCollection(newCollection);

            // 새로운 꽃 배정
            List<UserFlower> userFlowerList = userFlowerService.getUnlockedFlowerList(user);
            int randomNum = (int)(Math.random()*userFlowerList.size())+1;
            Flower newFlower = userFlowerList.get(randomNum).getFlower();

            int flowerCnt = collectionService.getCollectionCnt(user);

            System.out.println("user의 컬렉션 개수 : "+flowerCnt);

            Planting newPlanting = Planting.builder()
                    .flower(newFlower)
                    .user(user)
                    .exp(0)
                    .lv(0)
                    .flowerCnt(flowerCnt+1)
                    .build();
            plantingService.addPlanting(newPlanting);

            // 수확 웹소켓 알림
            System.out.println(user.getNickname()+" 님! 새로운 꽃 "+newFlower.getName()+" 이 배정되었습니다!");
        }

        // 레벨업 확인
        else if(nextLv > prevLv) { // 레벨업 이벤트 발생
            // 레벨업 웹소켓 알림
            System.out.println(user.getNickname()+" 님! 레벨 변화 "+prevLv + " -> "+nextLv);
        }

        // 변경사항 적용
        planting.setExp(nextExp);
        planting.setLv(nextLv);
        plantingService.modifyPlanting(planting);
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
                    // 유저의 해당 꽃 완전 해금
                    unlockUserFlower(user, flower, userFlower);

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
                    // 유저의 해당 꽃 완전 해금
                    unlockUserFlower(user, flower, userFlower);

                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 누적음수량 "+ flower.getDrinkValue()+"ml 달성으로 " + flower.getName() + " 꽃이 해금되었습니다!!!");
                }
            }
        }

    }

    public void checkStreakUnlock(User user) {
        // 현재 스트릭에 따른 달성여부 확인

        int curStreak = streakLogService.getDailyStreakLog(user).getCurStreak(); // 스트릭 변화에 따른 현재 스트릭
        List<UserFlower> userFlowerList = userFlowerService.getLockedFlowerList(user); // 해당 유저의 해금안된 꽃 리스트

        for (int i = 0; i < userFlowerList.size(); i++) { // 아직 해금 되지 않은 꽃들에 대하여
            UserFlower userFlower = userFlowerList.get(i);
            Flower flower = userFlower.getFlower();

            // 이전에는 스트릭 조건을 달성하지 못했었고, 현재 스트릭 조건을 달성했다면
            if(userFlower.isStreak() == false && curStreak >= flower.getStreakValue()) {
                // 해당 꽃의 스트릭 조건 해금
                userFlower.setStreak(true);
                userFlowerService.addUserFlower(userFlower);

                // 해금 기록 저장
                UserFlowerLog newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('s').build();
                userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                // 다른 조건들까지 모두 해금되었다면
                if(isFullyUnlocked(userFlower)) {
                    // 유저의 해당 꽃 완전 해금
                    unlockUserFlower(user, flower, userFlower);

                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 연속스트릭 "+ flower.getDrinkValue()+"일 달성으로 " + flower.getName() + " 꽃이 해금되었습니다!!!");
                }
            }
        }
    }

    public void checkCoasterUnlock(User user) {
        // 코스터 등록에 따른 달성여부 확인

        List<UserFlower> userFlowerList = userFlowerService.getLockedFlowerList(user); // 해당 유저의 해금안된 꽃 리스트

        for (int i = 0; i < userFlowerList.size(); i++) { // 아직 해금 되지 않은 꽃들에 대하여
            UserFlower userFlower = userFlowerList.get(i);
            Flower flower = userFlower.getFlower();

            // 이전에는 코스터 조건을 달성하지 못했었다면
            if(userFlower.isConnect() == false) {
                // 해당 꽃의 스트릭 조건 해금
                userFlower.setConnect(true);
                userFlowerService.addUserFlower(userFlower);

                // 해금 기록 저장
                UserFlowerLog newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('c').build();
                userFlowerLogService.addUserFlowerLog(newUserFlowerLog);

                // 다른 조건들까지 모두 해금되었다면
                if(isFullyUnlocked(userFlower)) {
                    // 유저의 해당 꽃 완전 해금
                    unlockUserFlower(user, flower, userFlower);

                    // 웹소켓 전송
                    System.out.println(user.getNickname()+" 님 코스터 등록으로 " + flower.getName() + " 꽃이 해금되었습니다!!!");
                }
            }
        }
    }


    public boolean checkStreakSuccess(User user) {
        int totalDailyDrink = drinkLogService.getDailyTotalDrink(user); // 유저의 오늘 누적음수량 조회
        StreakLog todayStreakLog = streakLogService.getDailyStreakLog(user); // 유저의 오늘 스트릭 가져오기

        // 아직 오늘의 스트릭이 생성된 적 없고 목표를 달성한 경우
        if(todayStreakLog == null && totalDailyDrink >= user.getGoal()) {
            // 오늘의 목표 달성 웹소켓 전송

            return true;
        }
        return false;
    }

    private boolean isFullyUnlocked(UserFlower userFlower) {
        // 모든 조건을 달성했다면 true 리턴
        if(userFlower.isFriend() && userFlower.isDrink() && userFlower.isStreak() && userFlower.isConnect()) {
            return true;
        }
        return false;
    }

    private void unlockUserFlower(User user, Flower flower, UserFlower userFlower) {
        // 유저의 해당 꽃 완전 해금
        userFlower.setUnlocked(true);
        userFlowerService.addUserFlower(userFlower);

        // 완전 해금 기록 저장
        UserFlowerLog newUserFlowerLog = UserFlowerLog.builder().user(user).flower(flower).mission('t').build();
        userFlowerLogService.addUserFlowerLog(newUserFlowerLog);
    }

}