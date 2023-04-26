package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {

}