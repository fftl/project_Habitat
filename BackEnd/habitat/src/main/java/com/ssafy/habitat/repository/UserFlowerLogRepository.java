package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.UserFlowerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFlowerLogRepository extends JpaRepository<UserFlowerLog, Integer> {
}
