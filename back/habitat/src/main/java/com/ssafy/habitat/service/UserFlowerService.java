package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.entity.UserFlower;
import com.ssafy.habitat.repository.UserFlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFlowerService {
    private UserFlowerRepository userFlowerRepository;

    @Autowired
    public UserFlowerService(UserFlowerRepository userFlowerRepository) {
        this.userFlowerRepository = userFlowerRepository;
    }

    public List<UserFlower> getLockedFlowerList(User user) {
        List<UserFlower> findUserLockedFlowerList = userFlowerRepository.findByUserAndIsUnlocked(user, false);
        return findUserLockedFlowerList;
    }

    public void addUserFlower(UserFlower newUserFlower) {
        userFlowerRepository.save(newUserFlower);
    }

    public List<UserFlower> getUnlockedFlowerList(User user) {
        List<UserFlower> findUserUnlockedFlowerList = userFlowerRepository.findByUserAndIsUnlocked(user, true);
        return findUserUnlockedFlowerList;
    }
}
