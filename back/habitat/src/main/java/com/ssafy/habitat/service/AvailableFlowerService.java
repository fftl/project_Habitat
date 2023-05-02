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

    public boolean addAvailableFlower(AvailableFlower availableFlower) {
        // 이미 가능한 꽃으로 등록되어 있다면 등록 안함
        if(availableFlowerRepository.findByUserAndFlower(availableFlower.getUser(), availableFlower.getFlower()) != null) {
            return false;
        }

        // 가능한 꽃 리스트에 추가
        availableFlowerRepository.save(availableFlower);
        return true;
    }

    public List<AvailableFlower> getAvailableFlowerList(User user) {
        // 유저의 획득한 꽃 목록 조회
        return user.getAvailableFlowerList();
    }
}
