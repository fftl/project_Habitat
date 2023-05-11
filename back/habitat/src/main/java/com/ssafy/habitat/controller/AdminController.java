package com.ssafy.habitat.controller;

import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import com.ssafy.habitat.service.FlowerService;
import com.ssafy.habitat.service.PlantingService;
import com.ssafy.habitat.service.UserFlowerService;
import com.ssafy.habitat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class AdminController {

    private UserService userService;
    private FlowerService flowerService;
    private UserFlowerService userFlowerService;
    private PlantingService plantingService;

    @Autowired
    public AdminController(UserService userService, FlowerService flowerService, UserFlowerService userFlowerService, PlantingService plantingService) {
        this.userService = userService;
        this.flowerService = flowerService;
        this.userFlowerService = userFlowerService;
        this.plantingService = plantingService;
    }

    @GetMapping("/userflower")
    @ApiOperation(value = "DB 관리용 : UserFlower 엔티티 생성", notes = "모든 유저에 대해 모든 꽃 userFlower 엔티티 초기 생성")
    public void makeUserFlower() {
        // 모든 유저에 대해 userFlower 엔티티 생성
        List<User> userList = userService.getAll();
        List<Flower> flowerList = flowerService.getFlowerList();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            for (int j = 0; j < flowerList.size(); j++) {
                Flower flower = flowerList.get(j);
                UserFlower newUserFlower = UserFlower.builder()
                        .connect(flower.isConnect())
                        .drink(flower.isDrink())
                        .friend(flower.isFriend())
                        .streak(flower.isStreak())
                        .isUnlocked(false)
                        .flower(flower)
                        .user(user)
                        .build();
                if(flower.getFlowerKey() == 1) {
                    newUserFlower.setUnlocked(true);
                }
                userFlowerService.addUserFlower(newUserFlower);

            }
        }
    }

    @GetMapping("/planting")
    @ApiOperation(value = "DB 관리용 : Planting 초기화", notes = "모든 유저에 대해 Plating 엔티티 1번 꽃으로 초기화")
    public void resetPlanting() {
        // 모든 유저에 대해 userFlower 엔티티 생성
        List<User> userList = userService.getAll();
        Flower flower = flowerService.getFlower(1);

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Planting newPlating = Planting.builder()
                    .exp(0)
                    .flowerCnt(1)
                    .lv(0)
                    .flower(flower)
                    .user(user)
                    .build();
            plantingService.addPlanting(newPlating);
        }
    }

    @GetMapping("/redis-test")
    @ApiOperation(value="Redis 테스트")
    public void redisAdd(){

    }
}
