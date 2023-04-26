package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
