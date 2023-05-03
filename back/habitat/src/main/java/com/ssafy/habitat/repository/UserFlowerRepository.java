package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFlowerRepository extends JpaRepository<UserFlower, Integer> {
    List<UserFlower> findByUserAndIsUnlocked(User user, boolean unlocked);
}
