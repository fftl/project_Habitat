package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestService {

    private FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void addFriendRequest(FriendRequest newFriendRequest) {
        // 이미 전송한 친구신청이 있는 경우
        if(friendRequestRepository.findByFromAndToAndStatus(newFriendRequest.getFrom(), newFriendRequest.getTo(), 0) != null) {
            throw new CustomException(ErrorCode.ALREADY_SENT_FRIEND_REQUEST);
        }

        friendRequestRepository.save(newFriendRequest);
    }

    public List<FriendRequest> getFriendRequestList(User user) {
        List<FriendRequest> friendRequestList = new ArrayList<>();

        for (int i = 0; i < user.getFriendRequestList().size(); i++) {
            FriendRequest friendRequest = user.getFriendRequestList().get(i);
            if(friendRequest.getStatus() == 0) { // 대기 중인 요청만 조회
                friendRequestList.add(friendRequest);
            }
        }

        return friendRequestList;
    }

    public FriendRequest getFriendRequestByRequestKey(int requestKey) {
        FriendRequest findFriendRequest = friendRequestRepository.findById(requestKey).orElseThrow(()->new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        return findFriendRequest;
    }

    public void checkFriendRequestAuthorization(User user, FriendRequest friendRequest) {
        // 해당 유저에게 귀속된 친구신청이 아닌 경우
        if(user != friendRequest.getTo()) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOR_USER);
        }
    }

    public void modifyFriendRequest(FriendRequest friendRequest, int status) {
        friendRequest.setStatus(status); // status 변경(1 : 수락, 2: 거절)
        friendRequestRepository.save(friendRequest);
    }
}