package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Coaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoasterRepository extends JpaRepository<Coaster, String> {

}