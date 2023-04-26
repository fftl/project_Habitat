package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoasterRepository extends JpaRepository<User, String> {

}