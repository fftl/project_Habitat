//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.UserCoaster;
//import com.ssafy.habitat.repository.UserCoasterRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//class UserCoasterServiceTest {
//
//    @Mock
//    UserCoasterRepository userCoasterRepository;
//    @InjectMocks
//    UserCoasterService userCoasterService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("유저 코스터 등록 테스트")
//    void addUserCoaster_SaveNewUserCoaster() {
//        // Given
//        UserCoaster userCoaster = new UserCoaster();
//
//        // When
//        userCoasterService.addUserCoaster(userCoaster);
//
//        // Then
//        verify(userCoasterRepository, times(1)).save(userCoaster);
//    }
//
//}