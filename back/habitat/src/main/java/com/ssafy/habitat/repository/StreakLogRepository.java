package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.StreakLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreakLogRepository extends JpaRepository<StreakLog, Integer> {
   }
