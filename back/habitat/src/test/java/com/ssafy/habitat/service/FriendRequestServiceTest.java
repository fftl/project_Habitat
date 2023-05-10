//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.Friend;
//import com.ssafy.habitat.entity.FriendRequest;
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.repository.FriendRequestRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FriendRequestServiceTest {
//    @Mock
//    FriendRequestRepository friendRequestRepository;
//    @InjectMocks
//    FriendRequestService friendRequestService;
//
//    private User fromUser;
//    private User toUser;
//
//    @BeforeEach
//    void setUp() {
//        System.out.println("test!");
//        MockitoAnnotations.openMocks(this);
//
//        // 테스트용 데이터 설정
//        fromUser = new User();
//        fromUser.setUserKey("1111");
//        toUser = new User();
//        toUser.setUserKey("2222");
//    }
//
//
//    @Test
//    void addFriendRequest() {
//        User fromUser = new User();
//        User toUser = new User();
//        toUser.setId(1L);
//        FriendRequest friendRequest = new FriendRequest(fromUser, toUser, 0);
//
//        when(friendRequestRepository.findByFromAndToAndStatus(fromUser, toUser, 0)).thenReturn(null);
//
//        friendRequestService.addFriendRequest(friendRequest);
//
//        verify(friendRequestRepository, times(1)).save(friendRequest);
//    }
//
//    @Test
//    void addFriendRequest_MySelf() {
//        User user = new User();
//        FriendRequest friendRequest = new FriendRequest(user, user, 0);
//
//        friendRequestService.addFriendRequest(friendRequest);
//    }
//
//    @Test
//    void addFriendRequest_AlreadySent() {
//        User fromUser = new User();
//        User toUser = new User();
//        toUser.setId(1L);
//        FriendRequest friendRequest = new FriendRequest(fromUser, toUser, 0);
//
//        when(friendRequestRepository.findByFromAndToAndStatus(fromUser, toUser, 0)).thenReturn(friendRequest);
//
//        friendRequestService.addFriendRequest(friendRequest);
//    }
//
//
//
//    @Test
//    void getFriendRequestList() {
//        User user = new User();
//        List<FriendRequest> friendRequestList = new ArrayList<>();
//        friendRequestList.add(new FriendRequest(user, new User(), 0));
//        friendRequestList.add(new FriendRequest(user, new User(), 1));
//        when(friendRequestRepository.findAllByTo(user)).thenReturn(friendRequestList);
//
//        List<FriendRequest> result = friendRequestService.getFriendRequestList(user);
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void getFriendRequestByRequestKey() {
//        int requestKey = 1;
//        FriendRequest friendRequest = new FriendRequest(new User(), new User(), 0);
//        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.of(friendRequest));
//
//        FriendRequest result = friendRequestService.getFriendRequestByRequestKey(requestKey);
//
//        assertEquals(friendRequest, result);
//    }
//
//    @Test
//    void getFriendRequestByRequestKey_RequestNotFound() {
//        int requestKey = 1;
//        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.empty());
//
//        friendRequestService.getFriendRequestByRequestKey(requestKey);
//    }
//
//    @Test
//    void checkFriendRequestAuthorization() {
//        User user = new User();
//        FriendRequest friendRequest = new FriendRequest(new User(), user, 0);
//
//        friendRequestService.checkFriendRequestAuthorization(user, friendRequest);
//
//    }
//
//    @Test
//    void checkFriendRequestAuthorization() {
//    }
//
//    @Test
//    void modifyFriendRequest() {
//    }
//}