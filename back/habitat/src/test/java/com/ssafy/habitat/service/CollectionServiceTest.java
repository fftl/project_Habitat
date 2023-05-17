//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.Collection;
//import com.ssafy.habitat.entity.User;
//import com.ssafy.habitat.repository.CollectionRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class CollectionServiceTest {
//
//    @Mock
//    private CollectionRepository collectionRepository;
//
//    @InjectMocks
//    private CollectionService collectionService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("유저 컬렉션 목록 조회 테스트")
//    void getCollectionList() {
//        // Given
//        User user = new User();
//        List<Collection> collectionList = new ArrayList<>();
//        collectionList.add(new Collection());
//        collectionList.add(new Collection());
//        user.setCollectionList(collectionList);
//
//        // When
//        List<Collection> result = collectionService.getCollectionList(user);
//
//        // Then
//        assertEquals(collectionList, result);
//
//    }
//
//    @Test
//    @DisplayName("새로운 컬렉션 등록 테스트")
//    void addCollection() {
//        // Given
//        Collection newCollection = new Collection();
//
//        // When
//        collectionService.addCollection(newCollection);
//
//        // Then
//        verify(collectionRepository).save(newCollection);
//    }
//
//    @Test
//    @DisplayName("컬렉션 개수 조회 테스트")
//    void getCollectionCnt() {
//        // Given
//        User user = new User();
//        Long count = 3L;
//        when(collectionRepository.countByUser(user)).thenReturn(count);
//
//        // When
//        int result = collectionService.getCollectionCnt(user);
//
//        // Then
//        assertEquals(count.intValue(), result);
//    }
//}