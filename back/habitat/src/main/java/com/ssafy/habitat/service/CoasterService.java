package com.ssafy.habitat.service;

import com.ssafy.habitat.repository.CoasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoasterService {

    private CoasterRepository coasterRepository;

    @Autowired
    public CoasterService(CoasterRepository coasterRepository) {
        this.coasterRepository = coasterRepository;
    }
}
