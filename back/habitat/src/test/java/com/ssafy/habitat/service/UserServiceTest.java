//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.exception.CustomException;
//import com.ssafy.habitat.exception.ErrorCode;
//import com.ssafy.habitat.repository.UserRepository;
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
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("유저 조회 테스트 (존재하는 유저)")
//    public void getUser_WhenUserExist_ReturnUser() {
//        // Given
//        String userKey = "user123";
//        User user = new User();
//        when(userRepository.findById(userKey)).thenReturn(Optional.of(user));
//
//        // When
//        User result = userService.getUser(userKey);
//
//        // Then
//        assertEquals(user, result);
//    }
//
//    @Test
//    @DisplayName("유저 조회 테스트 (존재하지 않는 유저)")
//    public void getUser_WhenUserNotExist_ThrowException() {
//        // Given
//        String userKey = "user123";
//        when(userRepository.findById(userKey)).thenReturn(Optional.empty());
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            userService.getUser(userKey);
//        });
//
//        assertEquals(ErrorCode.USER_KEY_NOT_FOUND, exception.getErrorCode());
//    }
//
//
//    @Test
//    @DisplayName("유저 키 체크 테스트 (사용 가능한 키)")
//    public void userKeyCheck_ValidKey_ReturnFalse() {
//        // Given
//        String newKey = "user123";
//        when(userRepository.findById(newKey)).thenReturn(Optional.empty());
//
//        // When
//        boolean result = userService.userKeyCheck(newKey);
//
//        // Then
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("유저 키 체크 테스트 (사용 중인 키)")
//    public void userKeyCheck_InvalidKey_ReturnTrue() {
//        // Given
//        String newKey = "user123";
//        when(userRepository.findById(newKey)).thenReturn(Optional.of(new User()));
//
//        // When
//        boolean result = userService.userKeyCheck(newKey);
//
//        // Then
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("친구코드 체크 테스트 (사용 가능한 코드)")
//    void friendCodeCheck_ValidCode_ReturnFalse() {
//        // Given
//        String newFriendCode = "friend123";
//        when(userRepository.findByFriendCode(newFriendCode)).thenReturn(null);
//
//        // When
//        boolean result = userService.friendCodeCheck(newFriendCode);
//
//        // Then
//        verify(userRepository, times(1)).findByFriendCode(newFriendCode);
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("친구코드 체크 테스트 (사용 중인 코드)")
//    void friendCodeCheck_InvalidCode_ReturnTrue() {
//        // Given
//        String newFriendCode = "friend123";
//        when(userRepository.findByFriendCode(newFriendCode)).thenReturn(Optional.of(new User()));
//
//        // When
//        boolean result = userService.friendCodeCheck(newFriendCode);
//
//        // Then
//        verify(userRepository, times(1)).findByFriendCode(newFriendCode);
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("소셜 키 체크 테스트 (존재하지 않는 유저)")
//    void socialKeyCheck_NotExistingSocialKey_ReturnTrue() {
//        // Given
//        String socialKey = "social123";
//        when(userRepository.findBySocialKey(socialKey)).thenReturn(null);
//
//        // When
//        boolean result = userService.socialKeyCheck(socialKey);
//
//        // Then
//        assertTrue(result);
//
//    }
//
//    @Test
//    @DisplayName("소셜 키 체크 테스트 (존재하는 유저)")
//    void socialKeyCheck_ExistingSocialKey_ReturnFalse() {
//        // Given
//        String socialKey = "social123";
//        when(userRepository.findBySocialKey(socialKey)).thenReturn(Optional.of(new User()));
//
//        // When
//        boolean result = userService.socialKeyCheck(socialKey);
//
//        // Then
//        assertFalse(result);
//    }
//
//
//    @Test
//    @DisplayName("친구코드로 유저 조회 테스트 (존재하는 유저)")
//    public void getUserByFriendCode_ExistingUser_ReturnUser() {
//        // Given
//        String friendCode = "friend123";
//        User user = new User();
//        when(userRepository.findByFriendCode(friendCode)).thenReturn(Optional.of(user));
//
//        // When
//        User result = userService.getByFriendCode(friendCode);
//
//        // Then
//        assertEquals(user, result);
//    }
//
//    @Test
//    @DisplayName("친구코드로 유저 조회 테스트 (존재하지 않는 유저)")
//    public void getUserByFriendCode_NonExistingUser_ThrowException() {
//        // Given
//        String friendCode = "friend123";
//        when(userRepository.findByFriendCode(friendCode)).thenReturn(Optional.empty());
//
//        // When, Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//        userService.getByFriendCode(friendCode);
//        });
//
//        assertEquals(ErrorCode.FRIEND_CODE_NOT_FOUND, exception.getErrorCode());
//}
//
//    @Test
//    @DisplayName("소셜 키로 유저 조회 테스트 (존재하는 유저)")
//    public void getUserBySocialKey_ExistingUser_ReturnUser() {
//        // Given
//        String socialKey = "social123";
//        User user = new User();
//        when(userRepository.findBySocialKey(socialKey)).thenReturn(Optional.of(user));
//
//        // When
//        User result = userService.getBySocialKey(socialKey);
//
//        // Then
//        assertEquals(user, result);
//    }
//
//    @Test
//    @DisplayName("소셜 키로 유저 조회 테스트 (존재하지 않는 유저)")
//    public void getUserBySocialKey_NotExistingUser_ThrowException() {
//        // Given
//        String socialKey = "social123";
//        when(userRepository.findBySocialKey(socialKey)).thenReturn(Optional.empty());
//
//        // When, Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            userService.getBySocialKey(socialKey);
//        });
//
//        assertEquals(ErrorCode.SOCIAL_CODE_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    @DisplayName("유저 추가 테스트")
//    public void addUser() {
//        // Given
//        User user = new User();
//
//        // When
//        userService.addUser(user);
//
//        // Then
//        verify(userRepository).save(user);
//    }
//
//    @Test
//    @DisplayName("유저 로드 테스트")
//    public void loadUserByUsername() {
//        // Given
//        String userKey = "user123";
//        User user = new User();
//        when(userRepository.findById(userKey)).thenReturn(Optional.of(user));
//
//        // When
//        User result = (User) userService.loadUserByUsername(userKey);
//
//        // Then
//        assertEquals(user, result);
//    }
//
//    @Test
//    @DisplayName("유저 로드 테스트")
//    public void loadUserByUsername_NotExistingUser_ThrowException() {
//        // Given
//        String userKey = "user123";
//        User user = new User();
//        when(userRepository.findById(userKey)).thenReturn(Optional.empty());
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            userService.loadUserByUsername(userKey);
//        });
//
//        // Then
//        assertEquals(ErrorCode.USER_KEY_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    @DisplayName("모든 유저 조회 테스트")
//    public void testGetAllUsers() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        List<User> userList = new ArrayList<>();
//        userList.add(user1);
//        userList.add(user2);
//        when(userRepository.findAll()).thenReturn(userList);
//
//        // When
//        List<User> result = userService.getAll();
//
//        // Then
//        assertEquals(userList, result);
//    }
//
//}