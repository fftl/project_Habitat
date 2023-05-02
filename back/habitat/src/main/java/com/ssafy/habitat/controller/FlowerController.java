package com.ssafy.habitat.controller;

import com.ssafy.habitat.dto.RequestFlowerDto;
import com.ssafy.habitat.dto.ResponseExpDto;
import com.ssafy.habitat.dto.ResponseFlowerDto;
import com.ssafy.habitat.entity.*;
import com.ssafy.habitat.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/flowers")
public class FlowerController {
    private FlowerService flowerService;
    private AvailableFlowerService availableFlowerService;
    private CollectionService collectionService;
    private PlantingService plantingService;
    private UserService userService;

    @Autowired
    public FlowerController(FlowerService flowerService, AvailableFlowerService availableFlowerService, CollectionService collectionService, PlantingService plantingService, UserService userService) {
        this.flowerService = flowerService;
        this.availableFlowerService = availableFlowerService;
        this.collectionService = collectionService;
        this.plantingService = plantingService;
        this.userService = userService;
    }


    @GetMapping("/exp")
    @ApiOperation(value = "리워드 페이지 조회(꽃, 경험치, 레벨)", notes="현재 유저의 꽃, 경험치, 레벨을 조회합니다.")
    public ResponseEntity getDrinkLog(@RequestParam("userKey") String userKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        Planting planting = plantingService.getPlant(user);
        Flower flower = planting.getFlower();

        // Entity -> Dto
        ResponseExpDto responseExpDto = ResponseExpDto.builder()
                .flowerKey(flower.getFlowerKey())
                .exp(planting.getExp())
                .maxExp(planting.getMax())
                .lv(planting.getLv())
                .build();
        ResponseFlowerDto responseFlowerDto = ResponseFlowerDto.builder()
                .flowerKey(flower.getFlowerKey())
                .name(flower.getName())
                .story(flower.getStory())
                .getCondition(flower.getGetCondition())
                .build();

        // Result 담을 HashMap
        HashMap<String, Object> map = new HashMap<>();
        map.put("exp", responseExpDto);
        map.put("flower", responseFlowerDto);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/{flowerKey}")
    @ApiOperation(value = "꽃 상세", notes="꽃 하나에 대한 상세 내용을 조회합니다.")
    public ResponseEntity getDrinkLog(@PathVariable("flowerKey") int flowerKey) {

        Flower flower = flowerService.getFlower(flowerKey);

        // Entity -> Dto
        ResponseFlowerDto responseFlowerDto = ResponseFlowerDto.builder()
                .flowerKey(flower.getFlowerKey())
                .name(flower.getName())
                .story(flower.getStory())
                .getCondition(flower.getGetCondition())
                .build();

        return new ResponseEntity<>(responseFlowerDto, HttpStatus.OK);
    }


    @PostMapping("/flower")
    @ApiOperation(value = "꽃 등록", notes="DB에 꽃을 등록합니다.")
    public ResponseEntity addFlower(@RequestBody RequestFlowerDto requestFlowerDto) {

        Flower flower = requestFlowerDto.toEntity();
        flowerService.addFlower(flower);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/available")
    @ApiOperation(value = "획득할 수 있는(해금) 꽃 목록", notes="유저가 획득할 수 있는 꽃 목록을 조회합니다.")
    public ResponseEntity getAvailableFlowerList(@RequestParam("userKey") String userKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // user가 해금한 꽃
        List<AvailableFlower> availableFlowerList = availableFlowerService.getAvailableFlowerList(user);

        // Entity -> Dto
        List<ResponseFlowerDto> responseFlowerDtoList = new ArrayList<>();
        for(int i = 0; i < availableFlowerList.size(); i++) {
            Flower flower = availableFlowerList.get(i).getFlower();
            ResponseFlowerDto responseFlowerDto = ResponseFlowerDto.builder()
                    .flowerKey(flower.getFlowerKey())
                    .name(flower.getName())
                    .story(flower.getStory())
                    .getCondition(flower.getGetCondition())
                    .build();
            responseFlowerDtoList.add(responseFlowerDto);
        }

        return new ResponseEntity<>(responseFlowerDtoList, HttpStatus.OK);
    }

    @GetMapping("/get")
    @ApiOperation(value = "수확한 꽃 목록", notes="유저가 수확한 꽃 목록을 중복없이 조회합니다.")
    public ResponseEntity getGetFlowerList(@RequestParam("userKey") String userKey) {
        User user = userService.getUser(userKey); // userKey의 유저를 찾습니다.

        // user가 획득한 꽃
        List<Collection> collectionList = collectionService.getGetFlowerList(user);

        // Entity -> Dto
        HashSet<Integer> flowerCheck = new HashSet<>(); // 중복없이 조회하기 위하여
        List<ResponseFlowerDto> responseFlowerDtoList = new ArrayList<>();
        for(int i = 0; i < collectionList.size(); i++) {
            Flower flower = collectionList.get(i).getFlower();

            // 이미 리스트에 있는 꽃이라면 중복제거를 위해 continue
            if(flowerCheck.contains(flower.getFlowerKey())) continue;
            flowerCheck.add(flower.getFlowerKey());

            ResponseFlowerDto responseFlowerDto = ResponseFlowerDto.builder()
                    .flowerKey(flower.getFlowerKey())
                    .name(flower.getName())
                    .story(flower.getStory())
                    .getCondition(flower.getGetCondition())
                    .build();
            responseFlowerDtoList.add(responseFlowerDto);
        }

        return new ResponseEntity<>(responseFlowerDtoList, HttpStatus.OK);
    }

}
