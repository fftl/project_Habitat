package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.FriendRequest;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FriendRequestRepository;
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
//        user1.setFriendRequestList(friendRequestList);
    }


    @Test
    @DisplayName("친구신청 테스트")
    void addFriendRequest_SaveNewFriendRequest() {
        // Given
        User fromUser = new User();
        User toUser = new User();
        FriendRequest newFriendRequest = FriendRequest.builder().from(fromUser).to(toUser).build();
        when(friendRequestRepository.findByFromAndToAndStatus(fromUser, toUser, 0)).thenReturn(null);

        // When
        assertDoesNotThrow(() -> {
            friendRequestService.addFriendRequest(newFriendRequest);
        });

        // Then
        verify(friendRequestRepository, times(1)).save(newFriendRequest);
    }

    @Test
    @DisplayName("친구신청 테스트(자기 자신에게 신청)")
    void addFriendRequest_WhenSendFrierndRequestToMySelf_ThrowException() {
        // Given
        User user = new User();
        FriendRequest newFriendRequest = FriendRequest.builder().from(user).to(user).build();

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            friendRequestService.addFriendRequest(newFriendRequest);
        });

        assertEquals(ErrorCode.FRIEND_REQUEST_NOT_FOR_MYSELF, exception.getErrorCode());
    }

    @Test
    @DisplayName("친구신청 테스트(이미 보낸 신청)")
    void addFriendRequest_WhenFriendRequestAlreadySent_ThrowException() {
        // Given
        User fromUser = new User();
        User toUser = new User();
        FriendRequest newFriendRequest = FriendRequest.builder().from(fromUser).to(toUser).build();
        when(friendRequestRepository.findByFromAndToAndStatus(fromUser, toUser, 0)).thenReturn(Optional.of(newFriendRequest));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            friendRequestService.addFriendRequest(newFriendRequest);
        });

        assertEquals(ErrorCode.ALREADY_SENT_FRIEND_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("친구신청 목록 조회 테스트")
    void getFriendRequestList_ReturnFriendRequestList() {
        // Given
        User user1 = new User();
        User user2 = new User();
        FriendRequest friendRequest1 = FriendRequest.builder().friendRequestKey(1).from(user1).to(user2).status(0).build();
        FriendRequest friendRequest2 = FriendRequest.builder().friendRequestKey(2).from(user3).to(user1).status(1).build();

//        List<FriendRequest> userFriendRequestList = user1.getFriendRequestList();
//        userFriendRequestList.add(friendRequest1);
//        userFriendRequestList.add(friendRequest2);

        List<FriendRequest> expectedFriendRequestList = new ArrayList<>();
        expectedFriendRequestList.add(friendRequest1); // status==0 인 신청기록만 조회되어야 함

        // When
        List<FriendRequest> friendRequestList = friendRequestService.getFriendRequestList(user1);

        // Then
        assertEquals(expectedFriendRequestList, friendRequestList);
    }

    @Test
    @DisplayName("친구신청 상세 조회 테스트")
    void getFriendRequestByRequestKey_WhenFriendRequestExists_ReturnFriendRequest() {
        // Given
        int requestKey = 1;
        FriendRequest expectedFriendRequest = friendRequest1;
        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.of(friendRequest1));

        // When
        FriendRequest result = friendRequestService.getFriendRequestByRequestKey(requestKey);

        // Then
        verify(friendRequestRepository, times(1)).findById(requestKey);
        assertEquals(expectedFriendRequest, result);
    }

    @Test
    @DisplayName("친구신청 상세 조회 테스트(존재하지 않는 내역)")
    void getFriendRequestByRequestKey_WhenFriendRequestDoesNotExist_ThrowException() {
        // Given
        int requestKey = 1;
        when(friendRequestRepository.findById(requestKey)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            friendRequestService.getFriendRequestByRequestKey(requestKey);
        });

        assertEquals(ErrorCode.FRIEND_REQUEST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트")
    void checkFriendRequestAuthorization() {
        // Given
        User user1 = new User();
        User user2 = new User();
        FriendRequest friendRequest = FriendRequest.builder().from(user2).to(user1).status(0).build();

        // When & Then
        assertDoesNotThrow(()-> {
            friendRequestService.checkFriendRequestAuthorization(user1, friendRequest);
        });

    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트(해당 유저에게 귀속된 신청 아님)")
    void checkFriendRequestAuthorization_WhenFriendRequestNotForUser_ThrowException() {
        // Given
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        FriendRequest friendRequest = FriendRequest.builder().from(user2).to(user3).build();

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            friendRequestService.checkFriendRequestAuthorization(user1, friendRequest);
        });

        assertEquals(ErrorCode.FRIEND_REQUEST_NOT_FOR_USER, exception.getErrorCode());

    }

    @Test
    @DisplayName("친구신청 수락/거절 유효성 테스트(이미 처리가 끝난 친구신청)")
    void checkFriendRequestAuthorization_WhenFriendRequestIsAlreadyHandled_ThrowException() {
        // Given
        User user1 = new User();
        User user2 = new User();
        FriendRequest friendRequest = FriendRequest.builder().from(user2).to(user1).status(1).build();

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            friendRequestService.checkFriendRequestAuthorization(user1, friendRequest);
        });

        assertEquals(ErrorCode.ALREADY_HANDLED_FRIEND_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("친구신청 상태 변경 테스트")
    void modifyFriendRequest_UpdateFriendRequest() {
        // Given
        int updateStatus = 1;
        FriendRequest friendRequest = new FriendRequest();
        when(friendRequestRepository.save(friendRequest)).thenReturn(null);

        // When
        friendRequestService.modifyFriendRequest(friendRequest, updateStatus);

        // Then
        verify(friendRequestRepository, times(1)).save(friendRequest);
    }
}