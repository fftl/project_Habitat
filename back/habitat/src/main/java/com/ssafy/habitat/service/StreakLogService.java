package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Collection;
import com.ssafy.habitat.entity.StreakLog;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.CoasterRepository;
import com.ssafy.habitat.repository.StreakLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class StreakLogService {

    private StreakLogRepository streakLogRepository;

    @Autowired
    public StreakLogService(StreakLogRepository streakLogRepository) {
        this.streakLogRepository = streakLogRepository;
    }

    public void addStreakLog(User user) {

        // 유저의 가장 최근 streak 가져오기
        List<StreakLog> streakLogList = user.getStreakLogList();

        StreakLog newStreakLog;

        // 만약 최근 streak이 하나도 없다면 바로 넣기
        if(streakLogList.size() == 0) {
            newStreakLog = StreakLog.builder()
                    .user(user)
                    .curStreak(1)
                    .maxStreak(1)
                    .build();
            streakLogRepository.save(newStreakLog);
            return;
        }


        // 최근 순으로 나열
        Collections.sort(streakLogList, new Comparator<StreakLog>() {
            @Override
            public int compare(StreakLog o1, StreakLog o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        StreakLog recentStreakLog = streakLogList.get(0);

        // 가장 최근 streak의 날짜가 오늘이라면 이미 목표 달성을 등록하였으므로 등록하지 않고 return null;
        LocalDate today = LocalDate.now();
        if(recentStreakLog.getCreatedAt().toLocalDate().isEqual(today)) {
            return;
        }

        // 가장 최근 streak의 날짜가 어제라면 연속하여 등록
        LocalDate yesterday = LocalDate.now().minusDays(1);
        if(recentStreakLog.getCreatedAt().toLocalDate().isEqual(yesterday)) {
            int curStreak = recentStreakLog.getCurStreak()+1;
            int maxStreak = Math.max(recentStreakLog.getMaxStreak(), curStreak);

            newStreakLog = StreakLog.builder()
                    .user(user)
                    .curStreak(curStreak)
                    .maxStreak(maxStreak)
                    .build();
            streakLogRepository.save(newStreakLog);
        }

        // 가장 최근 streak의 날짜가 어제보다 전이라면 연속 끊김 등록
        if(recentStreakLog.getCreatedAt().toLocalDate().isBefore(yesterday)) {
            int curStreak = 1;
            int maxStreak = Math.max(recentStreakLog.getMaxStreak(), curStreak);

            newStreakLog = StreakLog.builder()
                    .user(user)
                    .curStreak(curStreak)
                    .maxStreak(maxStreak)
                    .build();
            streakLogRepository.save(newStreakLog);
        }

    }
}
