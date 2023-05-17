package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FlowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerService {
    private final Logger LOGGER = LoggerFactory.getLogger(FlowerService.class);


    private FlowerRepository flowerRepository;

    @Autowired
    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public void addFlower(Flower flower) {
        LOGGER.info("addFlower() : 새로운 꽃 등록");

        flowerRepository.save(flower);
    }

    //    @Cacheable(value = "Flower", key = "#flowerKey", cacheManager = "cacheManager")
    public Flower getFlower(int flowerKey) {
        LOGGER.info("getFlower() : 꽃 객체 반환");

        Flower findFlower = flowerRepository.findById(flowerKey).orElseThrow(()->new CustomException(ErrorCode.FLOWER_NOT_FOUND));
        return findFlower;
    }

    @Cacheable(value="FlowerList")
    public List<Flower> getFlowerList() {
        LOGGER.info("getFlowerList() : 모든 꽃 목록 반환");

        List<Flower> flowerList = flowerRepository.findAll();
        return flowerList;
    }
}
