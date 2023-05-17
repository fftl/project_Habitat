package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.PlantingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantingService {
    private final Logger LOGGER = LoggerFactory.getLogger(PlantingService.class);


    private PlantingRepository plantingRepository;

    @Autowired
    public PlantingService(PlantingRepository plantingRepository) {
        this.plantingRepository = plantingRepository;
    }

    public Planting getCurrentPlant(User user, int flowerCnt) {
        LOGGER.info("getCurrentPlant() : 유저의 현재 키우는 꽃 정보 반환 - "+flowerCnt);

        // 현재 키우고 있는 꽃의 정보를 리턴
        Planting findPlanting = plantingRepository.findByUserAndFlowerCnt(user, flowerCnt+1)
                .orElseThrow(() -> new CustomException(ErrorCode.PLANTING_NOT_FOUND));

        return findPlanting;
    }

    public void modifyPlanting(Planting planting) {
        LOGGER.info("modifyPlanting() : 키우기 객체 수정");

        // 수정된 planting 객체 저장
        plantingRepository.save(planting);
    }

    public void addPlanting(Planting newPlanting) {
        LOGGER.info("addPlanting() : 새로운 키우기 꽃 등록");

        // 새로운 꽃 배정
        plantingRepository.save(newPlanting);
    }
}
