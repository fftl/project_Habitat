package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRepository extends JpaRepository<Friend, Integer> {

}