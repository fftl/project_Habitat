package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
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


    public void checkFriendRequestPossible(User fromUser, User toUser) {
        Friend findFriend = friendRepository.findByMyIdAndFriendId(fromUser, toUser).orElse(null);

        // 이미 친구 관계에 있음을 확인했다면 친구신청 불가
        if(findFriend != null) {
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }
    }

    public void addFriend(Friend newFriend) {
        friendRepository.save(newFriend);
    }
}