package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.UserCoaster;
import com.ssafy.habitat.repository.UserCoasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCoasterService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserCoasterService.class);


    private UserCoasterRepository userCoasterRepository;

    @Autowired
    public UserCoasterService(UserCoasterRepository userCoasterRepository) {
        this.userCoasterRepository = userCoasterRepository;
    }

    public void addUserCoaster(UserCoaster userCoaster) {
        userCoasterRepository.save(userCoaster);
    }
}
