package com.ssafy.habitat.service;

import com.ssafy.habitat.repository.AvailableFlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailableFlowerService {

    private AvailableFlowerRepository availableFlowerRepository;

    @Autowired
    public AvailableFlowerService(AvailableFlowerRepository availableFlowerRepository) {
        this.availableFlowerRepository = availableFlowerRepository;
    }
}
