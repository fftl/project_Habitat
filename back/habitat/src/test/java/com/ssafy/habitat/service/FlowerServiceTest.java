//package com.ssafy.habitat.service;
//
//import com.ssafy.habitat.entity.Flower;
//import com.ssafy.habitat.exception.CustomException;
//import com.ssafy.habitat.exception.ErrorCode;
//import com.ssafy.habitat.repository.FlowerRepository;
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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class FlowerServiceTest {
//
//    @Mock
//    FlowerRepository flowerRepository;
//    @InjectMocks
//    FlowerService flowerService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("꽃 등록 테스트")
//    void addFlower_SaveNewFlower() {
//        // Given
//        Flower flower = new Flower();
//
//        // When
//        flowerService.addFlower(flower);
//
//        // Then
//        verify(flowerRepository, times(1)).save(flower);
//    }
//
//    @Test
//    @DisplayName("꽃 상세 조회 테스트")
//    void getFlower_WhenFlowerExists_ReturnFlower() {
//        // Given
//        int flowerKey = 1;
//        Flower flower = new Flower();
//        flower.setFlowerKey(flowerKey);
//        when(flowerRepository.findById(flowerKey)).thenReturn(Optional.of(flower));
//
//        // When
//        Flower result = flowerService.getFlower(flowerKey);
//
//        // Then
//        verify(flowerRepository, times(1)).findById(flowerKey);
//        assertEquals(result, flower);
//    }
//
//    @Test
//    @DisplayName("꽃 상세 조회 테스트(존재하지 않는 꽃)")
//    void getFlower_WhenFlowerDoesNotExist_ThrowException() {
//        // Given
//        int flowerKey = 1;
//        Flower flower = new Flower();
//        flower.setFlowerKey(flowerKey);
//        when(flowerRepository.findById(flowerKey)).thenReturn(Optional.empty());
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            flowerService.getFlower(flowerKey);
//        });
//
//        verify(flowerRepository, times(1)).findById(flowerKey);
//        assertEquals(ErrorCode.FLOWER_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    @DisplayName("모든 꽃 조회 테스트")
//    void getFlowerList_ReturnFlowerList() {
//        // Given
//        List<Flower> flowerList = new ArrayList<>();
//        flowerList.add(new Flower());
//        when(flowerRepository.findAll()).thenReturn(flowerList);
//
//        // When
//        List<Flower> result = flowerService.getFlowerList();
//
//        // Then
//        verify(flowerRepository, times(1)).findAll();
//        assertEquals(result, flowerList);
//    }
//}