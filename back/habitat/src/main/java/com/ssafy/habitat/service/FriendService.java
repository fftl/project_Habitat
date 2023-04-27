package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public List<User> getFriendList(User user) {
        List<User> friendList = new ArrayList<>();

        for (int i = 0; i < user.getFriendList().size(); i++) {
            friendList.add(user.getFriendList().get(i).getFriendId());
        }

        return friendList;
    }
}