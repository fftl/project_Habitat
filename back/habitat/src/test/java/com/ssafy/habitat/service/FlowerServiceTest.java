package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Flower;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.repository.FlowerRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlowerServiceTest {

    @Mock
    FlowerRepository flowerRepository;
    @InjectMocks
    FlowerService flowerService;

    private Flower flower;
    private List<Flower> flowerList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트용 데이터 설정
        flower = new Flower();
        flower.setFlowerKey(1);
        flowerList = new ArrayList<>();
        flowerList.add(flower);
    }

    @Test
    @DisplayName("꽃 등록 테스트")
    void addFlower() {
        when(flowerRepository.save(flower)).thenReturn(null);
        flowerService.addFlower(flower);
    }

    @Test
    @DisplayName("꽃 상세 조회 테스트")
    void getFlower() {
        int flowerKey = 1;
        when(flowerRepository.findById(flowerKey)).thenReturn(Optional.of(flower));

        Assertions.assertDoesNotThrow(() -> {
            flowerService.getFlower(flowerKey);
        });

        Flower expectedFlower = flowerService.getFlower(flowerKey);
        Assertions.assertEquals(expectedFlower, flower);
    }

    @Test
    @DisplayName("꽃 상세 조회 테스트(존재하지 않는 꽃)")
    void getFlower_FlowerNotFound() {
        int flowerKey = 1;
        when(flowerRepository.findById(flowerKey)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomException.class, () -> {
            flowerService.getFlower(flowerKey);
        });

    }

    @Test
    @DisplayName("모든 꽃 조회 테스트")
    void getFlowerList() {
        when(flowerRepository.findAll()).thenReturn(flowerList);
        List<Flower> expectedFlowerList = flowerService.getFlowerList();
        Assertions.assertEquals(expectedFlowerList, flowerList);
    }
}