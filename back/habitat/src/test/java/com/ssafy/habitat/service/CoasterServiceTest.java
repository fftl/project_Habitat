package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Coaster;
import com.ssafy.habitat.exception.CustomException;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.repository.CoasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class CoasterServiceTest {
    @Mock
    private CoasterRepository coasterRepository;

    @InjectMocks
    private CoasterService coasterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("코스터 조회 테스트")
    public void getCoaster_ReturnCoaster() {
        // Given
        String coasterKey = "coaster123";
        Coaster coaster = new Coaster();
        when(coasterRepository.findById(coasterKey)).thenReturn(Optional.of(coaster));

        // When
        Coaster result = coasterService.getCoaster(coasterKey);

        // Then
        assertEquals(coaster, result);
    }

    @Test
    @DisplayName("코스터 조회 테스트(코스터 없음)")
    public void getCoaster() {
        // Given
        String coasterKey = "coaster123";
        when(coasterRepository.findById(coasterKey)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            coasterService.getCoaster(coasterKey);
        });

        // Then
        assertEquals(ErrorCode.COASTER_NOT_FOUND, exception.getErrorCode());
    }
}