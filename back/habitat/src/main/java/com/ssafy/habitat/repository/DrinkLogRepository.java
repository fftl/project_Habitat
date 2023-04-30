package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkLogRepository extends JpaRepository<DrinkLog, Integer> {
    DrinkLog findTop1ByUserOrderByCreatedAtDesc(User curUser);

    List<DrinkLog> findAllByUserAndIsRemoved(User user, boolean isRemoved);
}
