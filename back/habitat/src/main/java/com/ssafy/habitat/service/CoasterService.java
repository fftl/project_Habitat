package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
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

    public Coaster getCoaster(String coasterKey){
        Coaster coaster = coasterRepository.findById(coasterKey).orElseThrow(() -> new CustomException(ErrorCode.COASTER_NOT_FOUND));
        return coaster;
    }
}
