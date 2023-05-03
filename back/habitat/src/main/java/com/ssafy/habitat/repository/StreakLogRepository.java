package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.StreakLog;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StreakLogRepository extends JpaRepository<StreakLog, Integer> {
    Optional<StreakLog> findByUserAndCreatedAtBetween(User user, LocalDateTime startDatetime, LocalDateTime endDatetime);
}
