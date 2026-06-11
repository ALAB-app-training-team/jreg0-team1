package com.example.jreg0.Train;

import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.train.TrainController;
import com.example.jreg0.train.TrainEntity;
import com.example.jreg0.train.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TrainController.class)
public class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TrainService trainService;

    private Date departureDate;

    private TrainEntity trainEntity;

    // モックの設定
    List<ScheduleEntity> mockScheduleList = new ArrayList<ScheduleEntity>();

    ScheduleEntity mockSchedule0 = new ScheduleEntity();
    ScheduleEntity mockSchedule1 = new ScheduleEntity();

    TrainEntity mockTrain0 = new TrainEntity();

    @BeforeEach
    void setup() {
        departureDate = new Date(2026, 6, 3);
        //スケジュールのデータセット
        mockSchedule0.setId("00000000");
        mockSchedule0.setStationId("00000000");
        mockSchedule0.setDepartureTime(new Time(15, 00, 00));
        mockSchedule0.setArrivalTime(new Time(15, 10, 00));
        mockSchedule0.setDepartureTrack(1);
        mockSchedule0.setDepartureDate(new Date(2026, 6, 3));
        mockSchedule1.setId("00000001");
        mockSchedule1.setStationId("00000001");
        mockSchedule1.setDepartureTime(new Time(15, 15, 00));
        mockSchedule1.setArrivalTime(new Time(15, 25, 00));
        mockSchedule1.setDepartureTrack(1);
        mockSchedule1.setDepartureDate(new Date(2026, 6, 3));
        //列車のデータセット
        mockTrain0.setId("00000000");
        mockTrain0.setTrainNumber("0000");
        mockTrain0.setTrainName("hayabusa-0");
        mockTrain0.setRouteId("00000000");
        mockTrain0.setTrainNickname("はやぶさ");
        mockTrain0.setFormation(12);
        mockSchedule0.setTrain(mockTrain0);
        mockSchedule1.setTrain(mockTrain0);
        mockScheduleList.add(mockSchedule0);
        mockScheduleList.add(mockSchedule1);
        mockTrain0.setSchedules(mockScheduleList);
    }
    /**
     * 出発駅・到着駅・出発日に一致する列車を返却する*
     */
    @Test
    void getTrainByStationTest_NomalCase1() throws Exception {
        //列車取得のサービスモック定義
        when(trainService.getTrainByStation("00000000","00000001",any(Date.class))).thenReturn(List.of(mockTrain0));

        mockMvc.perform(get("/trains").param("start","00000000").param("end","00000001").param("date","2026-06-03")).andExpect(status().isOk());
        verify(trainService.getTrainByStation("00000000","00000001",departureDate));
    }
    /**
     * 出発駅・到着駅・出発日に一致する列車を返却する*
     */
    @Test
    void getTrainByStationTest_ErrorCase1() throws Exception {
        mockMvc.perform(get("/trains").param("start","00000000").param("end","00000001")).andExpect(status().isBadRequest());
    }

}
