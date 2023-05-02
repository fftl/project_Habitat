package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    List<Flower> findByFriend(boolean friend);
}
