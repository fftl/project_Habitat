package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.DrinkLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkLogService {
    private final Logger LOGGER = LoggerFactory.getLogger(DrinkLogService.class);

    private DrinkLogRepository drinkLogRepository;

    @Autowired
    public DrinkLogService(DrinkLogRepository drinkLogRepository) {
        this.drinkLogRepository = drinkLogRepository;
    }

    public LocalDateTime getRecentLog(User curUser) {
        DrinkLog recentLog = drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(curUser).orElse(null);
        if(recentLog == null) return null;
        else return recentLog.getCreatedAt();
    }

    public DrinkLog getLog(int drinkLogKey){
        DrinkLog drinkLog = drinkLogRepository.findById(drinkLogKey).orElseThrow(() -> new CustomException(ErrorCode.DRINK_LOG_NOT_FOUND));
        return drinkLog;
    }

    public List<DrinkLog> getAllLogs(User user) {
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndIsRemoved(user, false);
        return drinkLogList;
    }

    public List<DrinkLog> getDailyLogs(User user){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndAndIsRemovedAndModifiedAtBetween(user, false, startDatetime, endDatetime);

        return drinkLogList;
    }

    public void addDrinkLog(DrinkLog newDrinkLog) {
        drinkLogRepository.save(newDrinkLog);
    }

    public int getDailyTotalDrink(User user) {
        List<DrinkLog> dailyDrinkLogList = getDailyLogs(user);
        int total = 0;
        for (int i = 0; i < dailyDrinkLogList.size(); i++) {
            DrinkLog drinkLog = dailyDrinkLogList.get(i);
            total += drinkLog.getDrink();
        }
        return total;
    }

    public int getTotalDrink(User user) {
        List<DrinkLog> drinkLogList = getAllLogs(user);
        int total = 0;
        for (int i = 0; i < drinkLogList.size(); i++) {
            DrinkLog drinkLog = drinkLogList.get(i);
            total += drinkLog.getDrink();
        }
        return total;
    }
}