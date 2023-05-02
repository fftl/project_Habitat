package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.StreakLog;
import com.ssafy.habitat.repository.CoasterRepository;
import com.ssafy.habitat.repository.StreakLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreakLogService {

    private StreakLogRepository streakLogRepository;

    @Autowired
    public StreakLogService(StreakLogRepository streakLogRepository) {
        this.streakLogRepository = streakLogRepository;
    }

    public void addStreakLog(StreakLog streakLog) {
        // 유저의 가장 최근 streak 가져오기
    }
}
