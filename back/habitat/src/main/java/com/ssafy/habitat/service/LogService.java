package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Log;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {
    private LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LocalDateTime getRecentLog(User curUser) {
        Log recentLog = logRepository.findTop1ByUserOrderByCreatedAtDesc(curUser);
        if(recentLog == null) return null;
        else return recentLog.getCreatedAt();
    }
}