package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.DrinkLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    @Cacheable(value = "recent", key = "#curUser.getUserKey()", cacheManager = "cacheManager")
    public DrinkLog getRecentLog(User curUser) {
        LOGGER.info("getRecentLog() : 유저의 가장 최근 음수시간 반환");

        DrinkLog recentLog = drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(curUser).orElse(null);
        if(recentLog == null) return null;
        else return recentLog;
    }

    @Transactional
    @Cacheable(value = "recent", key = "#curUser.getUserKey()", cacheManager = "cacheManager")
    public DrinkLog getRecentLogNonCache(User curUser) {
        LOGGER.info("getRecentLog() : 유저의 가장 최근 음수시간 반환");

        DrinkLog recentLog = drinkLogRepository.findTop1ByUserOrderByCreatedAtDesc(curUser).orElse(null);
        if(recentLog == null) return null;
        else return recentLog;
    }

    public DrinkLog getLog(int drinkLogKey){
        LOGGER.info("getLog() : 특정 섭취 로그 반환");

        DrinkLog drinkLog = drinkLogRepository.findById(drinkLogKey).orElseThrow(() -> new CustomException(ErrorCode.DRINK_LOG_NOT_FOUND));
        return drinkLog;
    }

    @Transactional
    @Cacheable(value = "all", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public List<DrinkLog> getAllLogs(User user) {
        LOGGER.info("getAllLogs() : 유저의 모든 섭취 로그 반환");

        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndIsRemoved(user, false);
        return drinkLogList;
    }

    public List<DrinkLog> getAllLogsNonCache(User user) {
        LOGGER.info("getAllLogs() : 유저의 모든 섭취 로그 반환");

        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndIsRemoved(user, false);
        return drinkLogList;
    }

    @Transactional
    @Cacheable(value = "day", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public List<DrinkLog> getDailyLogs(User user){
        LOGGER.info("getDailyLogs() : 유저의 오늘 섭취 로그 전체 반환");

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndAndIsRemovedAndModifiedAtBetweenOrderByCreatedAtDesc(user, false, startDatetime, endDatetime);

        return drinkLogList;
    }

    @Transactional
    @Cacheable(value = "week", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public List<DrinkLog> getWeeklyLogs(User user){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)).minusDays(8); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)).minusDays(1); //오늘 23:59:59
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndAndIsRemovedAndModifiedAtBetweenOrderByCreatedAtAsc(user, false, startDatetime, endDatetime);

        return drinkLogList;
    }

    @Transactional
    @Cacheable(value = "month", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public List<DrinkLog> getMonthlyLogs(User user){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)).minusDays(31); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)).minusDays(1); //오늘 23:59:59
        List<DrinkLog> drinkLogList = drinkLogRepository.findAllByUserAndAndIsRemovedAndModifiedAtBetweenOrderByCreatedAtAsc(user, false, startDatetime, endDatetime);

        return drinkLogList;
    }

    @Transactional
    @CachePut(value = "recent", key = "#newDrinkLog.getUser().getUserKey()", cacheManager = "cacheManager")
    public DrinkLog addDrinkLog(DrinkLog newDrinkLog) {
        LOGGER.info("addDrinkLog() : 섭취 로그 등록");
        int dailyTotalDrink = getDailyTotalDrink(newDrinkLog.getUser());
        updateDailyTotalDrink(newDrinkLog.getUser(), newDrinkLog.getDrink()+dailyTotalDrink);

        int totalDrink = getTotalDrink(newDrinkLog.getUser());
        updateTotalDrink(newDrinkLog.getUser(), newDrinkLog.getDrink()+dailyTotalDrink);

        return drinkLogRepository.save(newDrinkLog);
    }

    @Transactional
    @Cacheable(value = "dayTotal", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public int getDailyTotalDrink(User user) {
        LOGGER.info("getDailyTotalDrink() : 유저의 일일 섭취 음수량 반환");

        List<DrinkLog> dailyDrinkLogList = getDailyLogs(user);
        int total = 0;
        for (int i = 0; i < dailyDrinkLogList.size(); i++) {
            DrinkLog drinkLog = dailyDrinkLogList.get(i);
            total += drinkLog.getDrink();
        }
        return total;
    }

    @Transactional
    @CachePut(value = "dayTotal", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public int updateDailyTotalDrink(User user, int value) {
        LOGGER.info("updateDailyTotalDrink() : 유저의 일일 섭취 음수량 반환");
        return value;
    }

    @Transactional
    @Cacheable(value = "stackTotal", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public int getTotalDrink(User user) {
        LOGGER.info("getTotalDrink() : 유저의 누적 섭취 음수량 반환");

        List<DrinkLog> drinkLogList = getAllLogs(user);
        int total = 0;
        for (int i = 0; i < drinkLogList.size(); i++) {
            DrinkLog drinkLog = drinkLogList.get(i);
            total += drinkLog.getDrink();
        }
        return total;
    }

    @Transactional
    @CachePut(value = "stackTotal", key = "#user.getUserKey()", cacheManager = "cacheManager")
    public int updateTotalDrink(User user, int value) {
        LOGGER.info("updateDailyTotalDrink() : 유저의 일일 섭취 음수량 반환");
        return value;
    }
}