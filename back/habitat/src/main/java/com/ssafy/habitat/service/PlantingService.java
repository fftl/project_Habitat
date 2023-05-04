package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.PlantingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantingService {

    private PlantingRepository plantingRepository;

    @Autowired
    public PlantingService(PlantingRepository plantingRepository) {
        this.plantingRepository = plantingRepository;
    }

    public Planting getCurrentPlant(User user) {
        // 현재 키우고 있는 꽃의 정보를 리턴
        Planting findPlanting = plantingRepository.findByUserAndFlowerCnt(user, user.getCollectionList().size()+1);
        if(findPlanting == null) {
            throw new CustomException(ErrorCode.PLANTING_NOT_FOUND);
        }

        return findPlanting;
    }

    public void modifyPlanting(Planting planting) {
        // 수정된 planting 객체 저장
        plantingRepository.save(planting);
    }

    public void addPlanting(Planting newPlanting) {
        // 새로운 꽃 배정
        plantingRepository.save(newPlanting);
    }
}
