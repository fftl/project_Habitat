package com.ssafy.habitat.service;

import com.ssafy.habitat.dto.ResponseUserDto;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.UserRepository;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Fetch(FetchMode.JOIN)
    @Cacheable(value = "User", key = "#userKey", cacheManager = "cacheManager", sync = true)
        public User getUser(String userKey) {
        LOGGER.info("getUser() : 유저키로 유저 조회하여 반환");

        User findUser = userRepository.findById(userKey).orElse(null);
        if(findUser == null) { // 존재하지 않는 유저
            throw new CustomException(ErrorCode.USER_KEY_NOT_FOUND);
        }
        return findUser;
    }

    //해당 key가 사용할 수 있는 key인지 판별합니다.
    public boolean userKeyCheck(String newKey) {
        LOGGER.info("userKeyCheck() : 유저키의 유효성 검사");

        return userRepository.findById(newKey).orElse(null) != null;
    }
    public boolean friendCodeCheck(String newFriendCode) {
        LOGGER.info("friendCodeCheck() : 친구코드의 유효성 검사");

        return userRepository.findByFriendCode(newFriendCode) != null;}
    public boolean socialKeyCheck(String socialKey) {
        LOGGER.info("socialKeyCheck() : 소셜키의 유효성 검사");

        return userRepository.findBySocialKey(socialKey) == null;}

    public User getByFriendCode(String code) {
        LOGGER.info("getByFriendCode() : 친구코드로 유저 조회하여 반환");

        User findUser = userRepository.findByFriendCode(code);
        if(findUser ==  null) { // 존재하지 않는 친구코드
            throw new CustomException(ErrorCode.FRIEND_CODE_NOT_FOUND);
        }
        return findUser;
    }

    public User getBySocialKey(String socialKey) {
        LOGGER.info("getBySocialKey() : 소셜키로 유저 조회하여 반환");

        User findUser = userRepository.findBySocialKey(socialKey);
        if(findUser ==  null) { // 존재하지 않는 친구코드
            throw new CustomException(ErrorCode.FRIEND_CODE_NOT_FOUND);
        }
        return findUser;
    }

    public User addUser(User user){
        LOGGER.info("addUser() : 새로운 유저 등록");

        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public UserDetails loadUserByUsername(String userKey) {
        LOGGER.info("loadUserByUsername() : 유저 키로 조회");

        User getUser = userRepository.findById(userKey).orElse(null);
        UserDetails userDetails = userRepository.findById(userKey)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_KEY_NOT_FOUND));
        return userDetails;
    }

    //아마 필요 없을 듯..?
    private UserDetails createUserDetails(User user) {
        LOGGER.info("createUserDetails() : 새로운 유저 객체 생성");

        UserDetails makeUser = User.builder()
                .userKey(user.getUserKey())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .roles(user.getRoles())
                .build();
        return makeUser;
    }

    public List<User> getAll() {
        LOGGER.info("getAll() : 모든 유저 목록 반환");

        return userRepository.findAll();
    }
}