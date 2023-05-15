package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.CoasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoasterService {
    private final Logger LOGGER = LoggerFactory.getLogger(CoasterService.class);

    private CoasterRepository coasterRepository;

    @Autowired
    public CoasterService(CoasterRepository coasterRepository) {
        this.coasterRepository = coasterRepository;
    }

    public Coaster getCoaster(String coasterKey){
        LOGGER.info("getCoaster() : 코스터 엔티티 반환 coasterKey="+coasterKey);

        Coaster coaster = coasterRepository.findById(coasterKey).orElseThrow(() -> new CustomException(ErrorCode.COASTER_NOT_FOUND));
        return coaster;
    }
}
