package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.AvailableFlower;
import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableFlowerRepository extends JpaRepository<AvailableFlower, Integer> {
    AvailableFlower findByUserAndFlower(User user, Flower flower);
}
