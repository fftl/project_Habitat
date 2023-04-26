package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.UserCoaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoasterRepository extends JpaRepository<UserCoaster, String> {

}