package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Planting;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.repository.PlantingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantingServiceTest {

    @Mock
    PlantingRepository plantingRepository;
    @InjectMocks
    PlantingService plantingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("현재 키우는 꽃 정보 조회 테스트")
    public void getCurrentPlant_ReturnPlanting() {
        // Given
        User user = new User();
        int flowerCnt = 1;
        Planting expectedPlanting = Planting.builder().build();
        when(plantingRepository.findByUserAndFlowerCnt(user, flowerCnt + 1)).thenReturn(Optional.of(expectedPlanting));

        // When
        Planting planting = plantingService.getCurrentPlant(user, flowerCnt);

        // Then
        assertEquals(expectedPlanting, planting);

    }

    @Test
    @DisplayName("현재 키우는 꽃 정보 조회 테스트(배정된 꽃 없음)")
    public void getCurrentPlant_WhenPlantingDoesNotExist_ThrowsException() {
        // Given
        User user = new User();
        int flowerCnt = 1;
        when(plantingRepository.findByUserAndFlowerCnt(user, flowerCnt + 1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> {
            plantingService.getCurrentPlant(user, flowerCnt);
        });

        verify(plantingRepository, times(1)).findByUserAndFlowerCnt(user, flowerCnt+ 1);
    }

    @Test
    @DisplayName("키우기 정보 수정 테스트")
    public void modifyPlanting_SaveModifiedPlanting() {
        // Given
        Planting planting = new Planting();

        // When
        plantingService.modifyPlanting(planting);

        // Then
        verify(plantingRepository, times(1)).save(planting);
    }

    @Test
    @DisplayName("새로운 키우기 꽃 배정 테스트")
    public void addPlanting_SaveNewPlanting() {
        // Given
        Planting newPlanting = new Planting();

        // When
        plantingService.addPlanting(newPlanting);

        // Then
        verify(plantingRepository, times(1)).save(newPlanting);
    }
}