package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Friend;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.FriendRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class FriendServiceTest {
    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendService friendService;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트용 데이터 설정
        user = new User();
        user.setUserKey("1111");
        user2 = new User();
        user2.setUserKey("2222");
        user3 = new User();
        user3.setUserKey("3333");
        List<Friend> userFriendList = new ArrayList<>();

        Friend friend1 = Friend.builder().myId(user).friendId(user2).build();
        Friend friend2 = Friend.builder().myId(user).friendId(user3).build();

        userFriendList.add(friend1);
        userFriendList.add(friend2);
        user.setFriendList(userFriendList);
    }

    @Test
    @DisplayName("친구목록 조회 테스트")
    void getFriendList() {
        // 테스트용 데이터를 이용하여 getFriendList() 메소드 테스트
        List<User> expectedFriendList = new ArrayList<>();
        expectedFriendList.add(user2);
        expectedFriendList.add(user3);

        List<User> friendList = friendService.getFriendList(user);
        Assertions.assertEquals(expectedFriendList, friendList);
    }

    @Test
    @DisplayName("친구신청 테스트")
    void testCheckFriendRequestPossible_NotFriend() {
        // 친구 관계가 아닌 경우를 테스트
        // repository mock 객체의 리턴값 설정
        when(friendRepository.findByMyIdAndFriendId(user, user2)).thenReturn(Optional.empty());

        // 예외 발생하지 않음 확인
        Assertions.assertDoesNotThrow(() -> {
            friendService.checkFriendRequestPossible(user, user2);
        });

        // 함수 호출(객체, 횟수) 확인
        verify(friendRepository, times(1)).findByMyIdAndFriendId(user, user2);
    }

    @Test
    @DisplayName("친구신청 테스트 (이미 친구인 경우)")
    void testCheckFriendRequestPossible_AlreadyFriend() {
        // 이미 친구 관계에 있는 경우를 테스트
        Friend friend = Friend.builder().myId(user).friendId(user2).build();
        // Optional.of(friend) : Optional 객체가 절대 Null일 수 없는 경우
        when(friendRepository.findByMyIdAndFriendId(user, user2)).thenReturn(Optional.of(friend));

        Assertions.assertThrows(CustomException.class, () -> {
            friendService.checkFriendRequestPossible(user, user2);
        });

        verify(friendRepository, times(1)).findByMyIdAndFriendId(user, user2);
    }

    @Test
    @DisplayName("친구관계 등록 테스트")
    void addFriend() {
        // 친구 관계가 아닌 경우를 테스트
        Friend newFriend = Friend.builder().myId(user).friendId(user2).build();
        when(friendRepository.findByMyIdAndFriendId(user, user2)).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> {
            friendService.addFriend(newFriend);
        });

        verify(friendRepository, times(1)).save(newFriend);
    }

    @Test
    @DisplayName("친구관계 등록 테스트(이미 친구인 경우)")
    void addFriend_AlreadyFriend() {
        // 친구 관계가 아닌 경우를 테스트
        Friend newFriend = Friend.builder().myId(user).friendId(user2).build();
        when(friendRepository.findByMyIdAndFriendId(user, user2)).thenReturn(Optional.of(newFriend));

        Assertions.assertThrows(CustomException.class, () -> {
            friendService.addFriend(newFriend);
        });
    }

}
