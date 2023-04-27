package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String userKey) {
        User findUser = userRepository.findById(userKey).orElse(null);
        if(findUser == null) {
//            throw new NotExistUserException();
        }
        return findUser;
    }
}