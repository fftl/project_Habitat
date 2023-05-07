package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByFriendCode(String code);

    User findBySocialKey(String socialKey);
}
