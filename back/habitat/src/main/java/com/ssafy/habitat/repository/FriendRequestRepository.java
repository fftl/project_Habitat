package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    FriendRequest findByFromAndToAndStatus(User from, User to, int status);
}