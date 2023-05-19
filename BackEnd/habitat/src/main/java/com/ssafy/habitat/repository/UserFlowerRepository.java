package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFlowerRepository extends JpaRepository<UserFlower, Integer> {
    List<UserFlower> findByUserAndIsUnlocked(User user, boolean unlocked);
}
