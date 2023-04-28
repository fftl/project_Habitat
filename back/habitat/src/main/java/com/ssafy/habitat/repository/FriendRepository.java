package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    Friend findByMyIdAndFriendId(User fromUser, User toUser);
}