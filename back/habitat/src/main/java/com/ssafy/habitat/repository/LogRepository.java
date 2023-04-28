package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Log;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
    Log findTop1ByUserOrderByCreatedAtDesc(User curUser);
}
