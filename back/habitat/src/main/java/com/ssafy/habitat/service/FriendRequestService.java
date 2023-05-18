package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FriendRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestService {
    private final Logger LOGGER = LoggerFactory.getLogger(FriendRequestService.class);


    private FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void checkFriendRequestPossible(FriendRequest newFriendRequest) {
        LOGGER.info("checkFriendRequestPossible() : 친구신청 가능 여부 확인");

        // 나 자신에게 친구신청을 하는 경우
        if(newFriendRequest.getTo() == newFriendRequest.getFrom()) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOR_MYSELF);
        }

        // 이미 전송한 친구신청이 있는 경우
        FriendRequest friendRequest = friendRequestRepository.findByFromAndToAndStatus(newFriendRequest.getFrom(), newFriendRequest.getTo(), 0).orElse(null);
        if(friendRequest != null) {
            throw new CustomException(ErrorCode.ALREADY_SENT_FRIEND_REQUEST);
        }

    }

    @Cacheable(value = "FriendRequestList", key = "#user.userKey")
    public List<FriendRequest> getFriendRequestList(User user) {
        LOGGER.info("getFriendRequestList() : 유저에게 도착한 친구신청 목록 반환");

        List<FriendRequest> friendRequestList = friendRequestRepository.findByToAndStatus(user,0);

        return friendRequestList;
    }

    @CacheEvict(value = "FriendRequestList", key = "#newFriendRequest.to.userKey")
    public void addFriendRequest(FriendRequest newFriendRequest) {
        LOGGER.info("addFriendRequest() : 친구신청 객체 등록");

        friendRequestRepository.save(newFriendRequest);
    }


    public FriendRequest getFriendRequestByRequestKey(int requestKey) {
        LOGGER.info("getFriendRequestByRequestKey() : 친구신청 객체 반환");

        FriendRequest findFriendRequest = friendRequestRepository.findById(requestKey).orElseThrow(()->new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        return findFriendRequest;
    }

    public void checkFriendRequestAuthorization(User user, FriendRequest friendRequest) {
        LOGGER.info("checkFriendRequestAuthorization() : 친구신청 처리의 유효성 검사");

        // 해당 유저에게 귀속된 친구신청이 아닌 경우
        if(user.getUserKey() != friendRequest.getTo().getUserKey()) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOR_USER);
        }

        // 이미 처리가 끝난 친구신청인 경우
        if(friendRequest.getStatus() != 0) {
            throw new CustomException(ErrorCode.ALREADY_HANDLED_FRIEND_REQUEST);
        }
    }

    @CacheEvict(value = "FriendRequestList", key = "#friendRequest.to.userKey")
    public void modifyFriendRequest(FriendRequest friendRequest, int status) {
        LOGGER.info("modifyFriendRequest() : 친구신청 상태 수정");

        friendRequest.setStatus(status); // status 변경(1 : 수락, 2: 거절)
        friendRequestRepository.save(friendRequest);
    }
}