package com.ssafy.habitat.service;

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
}
