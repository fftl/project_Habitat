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

    public Planting getPlant(User user) {
        Planting findPlanting = plantingRepository.findByUser(user);
        if(findPlanting == null) {
            throw new CustomException(ErrorCode.PLANTING_NOT_FOUND);
        }

        return findPlanting;
    }
}
