//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.Friend;
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.exception.CustomException;
//import com.ssafy.habitat.repository.FriendRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class FriendServiceTest {
//    @Mock
//    private FriendRepository friendRepository;
//
//    @InjectMocks
//    private FriendService friendService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("친구목록 조회 테스트")
//    void getFriendList_ReturnFriendList() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        User user3 = new User();
//
//        List<Friend> userFriendList = new ArrayList<>();
//
//        Friend friend1 = Friend.builder().myId(user1).friendId(user2).build();
//        Friend friend2 = Friend.builder().myId(user1).friendId(user3).build();
//
//        userFriendList.add(friend1);
//        userFriendList.add(friend2);
//        user1.setFriendList(userFriendList);
//
//        List<User> expectedFriendList = new ArrayList<>();
//        expectedFriendList.add(user2);
//        expectedFriendList.add(user3);
//
//        // When
//        List<User> friendList = friendService.getFriendList(user1);
//
//        // Then
//        assertEquals(expectedFriendList, friendList);
//    }
//
//    @Test
//    @DisplayName("친구신청 테스트")
//    void testCheckFriendRequestPossible_WhenFriendPossible() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        when(friendRepository.findByMyIdAndFriendId(user1, user2)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertDoesNotThrow(() -> {
//            friendService.checkFriendRequestPossible(user1, user2);
//        });
//
//        // Then
//        verify(friendRepository, times(1)).findByMyIdAndFriendId(user1, user2);
//    }
//
//    @Test
//    @DisplayName("친구신청 테스트 (이미 친구인 경우)")
//    void testCheckFriendRequestPossible_WhenAlreadyFriend_ThrowException() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        Friend friend = Friend.builder().myId(user1).friendId(user2).build();
//        // Optional.of(friend) : Optional 객체가 절대 Null일 수 없는 경우
//        when(friendRepository.findByMyIdAndFriendId(user1, user2)).thenReturn(Optional.of(friend));
//
//        // When & Then
//        assertThrows(CustomException.class, () -> {
//            friendService.checkFriendRequestPossible(user1, user2);
//        });
//
//        verify(friendRepository, times(1)).findByMyIdAndFriendId(user1, user2);
//    }
//
//    @Test
//    @DisplayName("친구관계 등록 테스트")
//    void addFriend_WhenFriendPossible_SaveNewFriend() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        Friend newFriend = Friend.builder().myId(user1).friendId(user2).build();
//        when(friendRepository.findByMyIdAndFriendId(user1, user2)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertDoesNotThrow(() -> {
//            friendService.addFriend(newFriend);
//        });
//
//        verify(friendRepository, times(1)).save(newFriend);
//    }
//
//    @Test
//    @DisplayName("친구관계 등록 테스트(이미 친구인 경우)")
//    void addFriend_WhenAlreadyFriend_ThrowException() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        Friend newFriend = Friend.builder().myId(user1).friendId(user2).build();
//        when(friendRepository.findByMyIdAndFriendId(user1, user2)).thenReturn(Optional.of(newFriend));
//
//        // When & Then
//        assertThrows(CustomException.class, () -> {
//            friendService.addFriend(newFriend);
//        });
//    }
//
//}
