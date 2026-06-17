package com.example.jreg0.Station;

import com.example.jreg0.station.StationEntity;
import com.example.jreg0.station.StationRepository;
import com.example.jreg0.station.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {
    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    StationEntity mockStation = new StationEntity();

    @BeforeEach
    void setup() {
        mockStation.setId("TKY01");
        mockStation.setStationName("東京");
    }

    @Test
    void getAll_NormalCase_駅を全件取得() {
        when(stationRepository.findAll()).thenReturn(List.of(mockStation));

        List<StationEntity> result = stationService.getAll();
        assertEquals(1, result.size());
        assertEquals(mockStation.getId(),result.getFirst().getId());
        verify(stationRepository).findAll();
    }
}
