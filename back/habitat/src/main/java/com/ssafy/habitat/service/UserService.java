package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
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
        if(findUser == null) { // 존재하지 않는 유저
            throw new CustomException(ErrorCode.USER_KEY_NOT_FOUND);
        }
        return findUser;
    }

    public User getByFriendCode(String code) {
        User findUser = userRepository.findByFriendCode(code);
        if(findUser == null) { // 존재하지 않는 친구코드
            throw new CustomException(ErrorCode.FRIEND_CODE_NOT_FOUND);
        }
        return findUser;
    }

}