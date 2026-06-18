package com.example.jreg0.Station;

import com.example.jreg0.station.StationEntity;
import com.example.jreg0.station.StationRepository;
import com.example.jreg0.station.StationService;
import com.example.jreg0.stopstation.StopStationEntity;
import com.example.jreg0.stopstation.StopStationIdEntity;
import com.example.jreg0.stopstation.StopStationRepository;
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
    @Mock
    private StopStationRepository stopStationRepository;

    @InjectMocks
    private StationService stationService;

    StationEntity mockStation = new StationEntity();
    StationEntity mockStationOnSameRoute = new StationEntity();
    StationEntity mockStationOnDifferenceRoute = new StationEntity();

    StopStationEntity mockStopStation1 = new StopStationEntity();
    StopStationEntity mockStopStation2 = new StopStationEntity();
    StopStationEntity mockStopStation3 = new StopStationEntity();
    StopStationEntity mockStopStation4 = new StopStationEntity();
    StopStationIdEntity mockStopStationId1 = new StopStationIdEntity();
    StopStationIdEntity mockStopStationId2 = new StopStationIdEntity();
    StopStationIdEntity mockStopStationId3 = new StopStationIdEntity();
    StopStationIdEntity mockStopStationId4 = new StopStationIdEntity();


    @BeforeEach
    void setup() {
        mockStation.setId("SND11");
        mockStation.setStationName("仙台");
        mockStopStationId1.setRouteId("THK01");
        mockStopStationId1.setStationId(mockStation.getId());
        mockStopStation1.setId(mockStopStationId1);

        mockStationOnSameRoute.setId("TKY01");
        mockStationOnSameRoute.setStationName("東京");
        mockStopStationId2.setRouteId("THK01");
        mockStopStationId2.setStationId(mockStationOnSameRoute.getId());
        mockStopStation2.setId(mockStopStationId2);
        mockStopStationId3.setRouteId("JET02");
        mockStopStationId3.setStationId(mockStationOnSameRoute.getId());
        mockStopStation3.setId(mockStopStationId3);

        mockStationOnDifferenceRoute.setId("GAL32");
        mockStationOnDifferenceRoute.setStationName("ガーラ湯沢");
        mockStopStationId4.setRouteId("JET02");
        mockStopStationId4.setStationId(mockStationOnDifferenceRoute.getId());
        mockStopStation4.setId(mockStopStationId4);
    }

    @Test
    void getAll_NormalCase_駅を全件取得() {
        when(stationRepository.findAll()).thenReturn(List.of(mockStation));

        List<StationEntity> result = stationService.getAll();
        assertEquals(1, result.size());
        assertEquals(mockStation.getId(),result.getFirst().getId());
        verify(stationRepository).findAll();
    }

    @Test
    void getStationsOnSameRouteById_NormalCase_駅Idと一致した経路内の駅取得(){
        String stationId = "SND11";
        when(stopStationRepository.findByIdStationId(stationId)).thenReturn(List.of(mockStopStation1));
        when(stopStationRepository.findByIdRouteId("THK01")).thenReturn(List.of(mockStopStation1,mockStopStation2));
        when(stationRepository.findAllById(List.of(stationId, "TKY01"))).thenReturn(List.of(mockStation, mockStationOnSameRoute));

        List<StationEntity> stationEntities = stationService.getStationsOnSameRouteById(stationId);
        assertEquals(List.of(mockStation, mockStationOnSameRoute), stationEntities);
        verify(stopStationRepository).findByIdStationId(stationId);
        verify(stopStationRepository).findByIdRouteId("THK01");
        verify(stationRepository).findAllById(List.of(stationId, "TKY01"));
    }
}
