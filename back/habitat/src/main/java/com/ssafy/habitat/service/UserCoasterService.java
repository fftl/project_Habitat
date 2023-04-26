package com.ssafy.habitat.service;

import com.ssafy.habitat.repository.UserCoasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCoasterService {

    private UserCoasterRepository userCoasterRepository;

    @Autowired
    public UserCoasterService(UserCoasterRepository userCoasterRepository) {
        this.userCoasterRepository = userCoasterRepository;
    }
}
