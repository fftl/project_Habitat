package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.repository.FriendRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FriendRequestServiceTest {
    @Mock
    FriendRequestRepository friendRequestRepository;
    @InjectMocks
    FriendRequestService friendRequestService;

    private User user1;
    private User user2;
    private User user3;
    private FriendRequest friendRequest1;
    private FriendRequest friendRequest2;
    private FriendRequest friendRequest3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트용 데이터 설정
        user1 = new User();
        user1.setUserKey("1111");
        user2 = new User();
        user2.setUserKey("2222");
        user3 = new User();
        user3.setUserKey("3333");

        List<FriendRequest> friendRequestList = new ArrayList<>();

        friendRequest1 = FriendRequest.builder().friendRequestKey(1).from(user2).to(user1).status(0).build();
        friendRequest2 = FriendRequest.builder().friendRequestKey(2).from(user3).to(user1).status(1).build();
        friendRequest3 = FriendRequest.builder().friendRequestKey(2).from(user1).to(user2).status(0).build();

        friendRequestList.add(friendRequest1);
        friendRequestList.add(friendRequest2);
        user1.setFriendRequestList(friendRequestList);
    }


    @Test
    @DisplayName("친구신청 테스트")
    void addFriendRequest() {

        FriendRequest friendRequest = FriendRequest.builder().from(user1).to(user2).build();
        when(friendRequestRepository.findByFromAndToAndStatus(user1, user2, 0)).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> {
            friendRequestService.addFriendRequest(friendRequest);
        });


        verify(friendRequestRepository, times(1)).save(friendRequest);
    }

    @Test
    @DisplayName("친구신청 테스트(자기 자신에게 신청)")
    void addFriendRequest_MySelf() {
        FriendRequest friendRequest = FriendRequest.builder().from(user1).to(user1).build();

        Assertions.assertThrows(CustomException.class, () -> {
            friendRequestService.addFriendRequest(friendRequest);
        });

    }

    @Test
    @DisplayName("친구신청 테스트(이미 보낸 신청)")
    void addFriendRequest_AlreadySent() {
        FriendRequest friendRequest = FriendRequest.builder().from(user1).to(user2).build();
        when(friendRequestRepository.findByFromAndToAndStatus(user1, user2, 0)).thenReturn(Optional.of(friendRequest));

        Assertions.assertThrows(CustomException.class, () -> {
            friendRequestService.addFriendRequest(friendRequest);
        });
    }

    @Test
    @DisplayName("친구신청 목록 조회 테스트")
    void getFriendRequestList() {
        List<FriendRequest> expectedFriendRequestList = new ArrayList<>();
        expectedFriendRequestList.add(friendRequest1); // status==0 인 신청기록만 조회되어야 함

        List<FriendRequest> friendRequestList = friendRequestService.getFriendRequestList(user1);

        Assertions.assertEquals(expectedFriendRequestList, friendRequestList);
    }

    @Test
    @DisplayName("친구신청 상세 조회 테스트")
    void getFriendRequestByRequestKey() {
        int requestKey = 1;
        FriendRequest expectedFriendRequest = friendRequest1;
        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.of(friendRequest1));

        Assertions.assertDoesNotThrow(()-> {
            friendRequestService.getFriendRequestByRequestKey(requestKey);
        });

        FriendRequest result = friendRequestService.getFriendRequestByRequestKey(requestKey);

        assertEquals(expectedFriendRequest, result);
    }

    @Test
    @DisplayName("친구신청 상세 조회 테스트(존재하지 않는 내역)")
    void getFriendRequestByRequestKey_RequestNotFound() {
        int requestKey = 1;
        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomException.class, () -> {
            friendRequestService.getFriendRequestByRequestKey(requestKey);
        });
    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트")
    void checkFriendRequestAuthorization() {
        Assertions.assertDoesNotThrow(() -> {
            friendRequestService.checkFriendRequestAuthorization(user1, friendRequest1);
        });
    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트(해당 유저에게 귀속된 신청 아님)")
    void checkFriendRequestAuthorization_NotForUser() {
        Assertions.assertThrows(CustomException.class, () -> {
            friendRequestService.checkFriendRequestAuthorization(user1, friendRequest3);
        });

    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트(이미 처리가 끝난 친구신청)")
    void checkFriendRequestAuthorization_AlreadyHandled() {
        Assertions.assertThrows(CustomException.class, () -> {
           friendRequestService.checkFriendRequestAuthorization(user1, friendRequest2);
        });
    }

    @Test
    @DisplayName("친구신청 상태 변경 테스트")
    void modifyFriendRequest() {
        when(friendRequestRepository.save(friendRequest1)).thenReturn(null);
        friendRequestService.modifyFriendRequest(friendRequest1, 1);
    }
}