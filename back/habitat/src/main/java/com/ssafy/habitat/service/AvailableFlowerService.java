package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.AvailableFlower;
import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.AvailableFlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableFlowerService {

    private AvailableFlowerRepository availableFlowerRepository;

    @Autowired
    public AvailableFlowerService(AvailableFlowerRepository availableFlowerRepository) {
        this.availableFlowerRepository = availableFlowerRepository;
    }

    public void addAvailableFlower(AvailableFlower availableFlower) {
        // 이미 가능한 꽃으로 등록되어 있다면 등록 안함
        if(availableFlowerRepository.findByUserAndFlower(availableFlower.getUser(), availableFlower.getFlower()) != null) {
            throw new CustomException(ErrorCode.ALREADY_AVAILABLE_FLOWER);
        }

        // 획득 꽃으로 등록
        availableFlowerRepository.save(availableFlower);
    }

    public List<AvailableFlower> getAvailableFlowerList(User user) {
        // 유저의 획득한 꽃 목록 조회
        return user.getAvailableFlowerList();
    }
}
