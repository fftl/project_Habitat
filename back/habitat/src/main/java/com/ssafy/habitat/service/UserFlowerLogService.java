package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.UserFlowerLog;
import com.ssafy.habitat.repository.UserFlowerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFlowerLogService {
    private UserFlowerLogRepository userFlowerLogRepository;

    @Autowired
    public UserFlowerLogService(UserFlowerLogRepository userFlowerLogRepository) {
        this.userFlowerLogRepository = userFlowerLogRepository;
    }

    public void addUserFlowerLog(UserFlowerLog newUserFlowerLog) {
        userFlowerLogRepository.save(newUserFlowerLog);
    }
}
