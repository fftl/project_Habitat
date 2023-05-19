package com.ssafy.habitat.repository;

import com.ssafy.habitat.entity.Coaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoasterRepository extends JpaRepository<Coaster, String> {

}