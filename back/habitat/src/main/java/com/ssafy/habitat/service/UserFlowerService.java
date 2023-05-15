package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import com.ssafy.habitat.repository.UserFlowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFlowerService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserFlowerService.class);

    private UserFlowerRepository userFlowerRepository;

    @Autowired
    public UserFlowerService(UserFlowerRepository userFlowerRepository) {
        this.userFlowerRepository = userFlowerRepository;
    }

    public List<UserFlower> getLockedFlowerList(User user) {
        LOGGER.info("getLockedFlowerList() : 유저의 잠긴 꽃 목록 반환");

        List<UserFlower> findUserLockedFlowerList = userFlowerRepository.findByUserAndIsUnlocked(user, false);
        return findUserLockedFlowerList;
    }

    public void addUserFlower(UserFlower newUserFlower) {
        LOGGER.info("addUserFlower() : 새로운 유저-꽃 관계 등록");

        userFlowerRepository.save(newUserFlower);
    }

    public List<UserFlower> getUnlockedFlowerList(User user) {
        LOGGER.info("getUnlockedFlowerList() : 유저의 해금된 꽃 목록 반환");

        List<UserFlower> findUserUnlockedFlowerList = userFlowerRepository.findByUserAndIsUnlocked(user, true);
        return findUserUnlockedFlowerList;
    }
}
