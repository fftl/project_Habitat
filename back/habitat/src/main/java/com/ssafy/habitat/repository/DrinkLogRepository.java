package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.DrinkLog;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DrinkLogRepository extends JpaRepository<DrinkLog, Integer> {
    Optional<DrinkLog> findTop1ByUserOrderByCreatedAtDesc(User curUser);
    List<DrinkLog> findAllByUserAndIsRemoved(User user, boolean isRemoved);
    List<DrinkLog> findAllByUserAndAndIsRemovedAndModifiedAtBetweenOrderByCreatedAtAsc(User user, boolean isRemoved, LocalDateTime start, LocalDateTime end);
    List<DrinkLog> findAllByUserAndAndIsRemovedAndModifiedAtBetweenOrderByCreatedAtDesc(User user, boolean isRemoved, LocalDateTime start, LocalDateTime end);
}
