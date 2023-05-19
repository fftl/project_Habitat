package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByFriendCode(String code);

    Optional<User> findBySocialKey(String socialKey);
}
