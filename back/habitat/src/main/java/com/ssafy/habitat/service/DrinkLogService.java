package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.repository.DrinkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class DrinkLogService {
    private DrinkLogRepository drinkLogRepository;

    @Autowired
    public DrinkLogService(DrinkLogRepository drinkLogRepository) {
        this.drinkLogRepository = drinkLogRepository;
    }

    public LocalDateTime getRecentLog(User curUser) {
        DrinkLog recentLog = drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(curUser);
        if(recentLog == null) return null;
        else return recentLog.getCreatedAt();
    }

    public List<DrinkLog> getAllLogs(User user) {
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndIsRemoved(user, false);
        return drinkLogList;
    }

    public List<DrinkLog> getDailyLogs(User user){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndModifiedAtBetween(user, startDatetime, endDatetime);

        return drinkLogList;
    }

    public void addDrinkLog(DrinkLog newDrinkLog) {
        drinkLogRepository.save(newDrinkLog);
    }
}