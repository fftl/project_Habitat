package com.ssafy.habitat.controller;

import com.ssafy.habitat.service.AvailableFlowerService;
import com.ssafy.habitat.service.CollectionService;
import com.ssafy.habitat.service.FlowerService;
import com.ssafy.habitat.service.PlantingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flowers")
public class FlowerController {
    private FlowerService flowerService;
    private AvailableFlowerService availableFlowerService;
    private CollectionService collectionService;
    private PlantingService plantingService;

    @Autowired
    public FlowerController(FlowerService flowerService, AvailableFlowerService availableFlowerService, CollectionService collectionService, PlantingService plantingService) {
        this.flowerService = flowerService;
        this.availableFlowerService = availableFlowerService;
        this.collectionService = collectionService;
        this.plantingService = plantingService;
    }
}
