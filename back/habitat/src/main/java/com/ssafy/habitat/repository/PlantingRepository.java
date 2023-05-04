package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantingRepository extends JpaRepository<Planting, Integer> {
    Planting findByUser(User user);

    Planting findByUserAndFlowerCnt(User user, int flowerCnt);
}
