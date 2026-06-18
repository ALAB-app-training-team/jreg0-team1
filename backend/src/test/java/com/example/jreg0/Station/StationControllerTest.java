package com.example.jreg0.Station;

import com.example.jreg0.station.StationController;
import com.example.jreg0.station.StationEntity;
import com.example.jreg0.station.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StationController.class)
public class StationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StationService stationService;

    StationEntity mockStation = new StationEntity();

    @BeforeEach
    void setup() {
        mockStation.setId("TKY01");
        mockStation.setStationName("東京");
    }

    @Test
    void getStations_NormalCase_すべての駅を返却する() throws Exception {
        when(stationService.getAll()).thenReturn(List.of(mockStation));

        mockMvc.perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockStation.getId()));
        verify(stationService).getAll();
    }

    @Test
    void getStationsOnSameRouteById_NormalCase_駅Idと一致した経路内の駅取得() throws  Exception {
        when(stationService.getStationsOnSameRouteById(mockStation.getId())).thenReturn(List.of(mockStation));

        mockMvc.perform(get("/stations/"+mockStation.getId()+"/reachable-stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockStation.getId()));
        verify(stationService).getStationsOnSameRouteById(mockStation.getId());
    }
}
