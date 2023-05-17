package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FriendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private final Logger LOGGER = LoggerFactory.getLogger(FriendService.class);

    private FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Cacheable(value = "FriendList", key = "#user.getUserKey()", cacheManager = "cacheManager", sync = true)
    public List<User> getFriendList(User user) {
        LOGGER.info("getFriendList() : 유저의 친구 목록 반환");


        List<Friend> friendList = friendRepository.findByMyId(user);
        List<User> friendUserList = new ArrayList<>();

        for (int i = 0; i < friendList.size(); i++) {
            friendUserList.add(friendList.get(i).getFriendId());
        }

        return friendUserList;
    }


    public void checkFriendRequestPossible(User fromUser, User toUser) {
        LOGGER.info("checkFriendRequestPossible() : 친구신청의 유효성 검사");

        Friend findFriend = friendRepository.findByMyIdAndFriendId(fromUser, toUser).orElse(null);

        // 이미 친구 관계에 있음을 확인했다면 친구신청 불가
        if(findFriend != null) {
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }
    }

    @CacheEvict(value = "FriendList", key = "#newFriend.myId.userKey")
    public void addFriend(Friend newFriend) {
        LOGGER.info("addFriend() : 새로운 친구관계 등록");

        Friend findFriend = friendRepository.findByMyIdAndFriendId(newFriend.getMyId(), newFriend.getFriendId()).orElse(null);
        // 이미 친구 관계에 있음을 확인했다면 친구관계 등록 불가
        if(findFriend != null) {
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }

        friendRepository.save(newFriend);
    }

    public int getFriendCnt(User user) {
        LOGGER.info("getFriendCnt() : 유저의 친구 수 반환");
        int friendCnt = friendRepository.countByMyId(user);
        return friendCnt;
    }
}