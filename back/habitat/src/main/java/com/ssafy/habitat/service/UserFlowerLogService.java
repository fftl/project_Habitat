package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.UserFlowerLog;
import com.ssafy.habitat.repository.UserFlowerLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFlowerLogService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserFlowerLogService.class);

    private UserFlowerLogRepository userFlowerLogRepository;

    @Autowired
    public UserFlowerLogService(UserFlowerLogRepository userFlowerLogRepository) {
        this.userFlowerLogRepository = userFlowerLogRepository;
    }

    public void addUserFlowerLog(UserFlowerLog newUserFlowerLog) {
        LOGGER.info("addUserFlowerLog() : 유저의 해금 기록 저장");

        userFlowerLogRepository.save(newUserFlowerLog);
    }
}
