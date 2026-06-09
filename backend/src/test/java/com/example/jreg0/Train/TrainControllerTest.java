package com.example.jreg0.Train;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.train.TrainEntity;
import com.example.jreg0.train.TrainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import sample.entity.ExampleEntity;
import sample.service.ExampleService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

// WebMvcTestアノテーションで、ExampleControllerのみをテスト対象に限定してWeb層のテストを設定。
@WebMvcTest(controllers = ExampleController.class)
class TrainControllerTest {

    // MockMvcをDIで取得し、HTTPリクエスト/レスポンスのテストを行う。
    @Autowired
    private MockMvc mockMvc;

    // TrainServiceのモックを作成してDI。モックの動作を設定してテストに利用。
    @MockitoBean
    private TrainService trainService;


    @Test
    void TrainControllerTest_1() throws Exception {
//        INSERT INTO train(id, train_number, train_name, route_id, train_nickname, formation)
//        values ('00000000', '0000', 'hayate-0', '00000000','hayate', 12),
        // モックの設定
        List<TrainEntity> mockResponse = new ArrayList<TrainEntity>();

        ScheduleEntity mockSchedule1 = new ScheduleEntity();
        TrainEntity mockTrain1 = new TrainEntity();

//        INSERT INTO schedule(id, train_id, station_id, departure_time, arrival_time,departure_track,departure_date)
//        values ('00000000', '00000000', '00000000', '15:00:00', '15:10:00',1,'2026-06-03'),
        mockSchedule1.setId("00000000");
        mockSchedule1.setTrain(mockTrain1);
        mockSchedule1.setStationId("00000000");
        mockSchedule1.setDepartureTime(Time(15:00:00));

        mockTrain1.setId("00000000");
        mockTrain1.setTrainNumber("0000");
        mockTrain1.setTrainName("hayabusa-0");
        mockTrain1.setRouteId("00000000");
        mockTrain1.setTrainNickname("はやぶさ");
        mockTrain1.setFormation(12);
        mockResponse.setId("00000000");
        mockResponse.
        mockResponse.setMessage("Mocked Example " + testId);

        // モックされたexampleServiceのgetExampleByIdメソッドが呼ばれたとき、mockResponseを返すよう設定。
        when(exampleService.getExampleById(testId)).thenReturn(mockResponse);

        // テスト実行
        mockMvc.perform(get("/api/example/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())            // HTTPステータスコード200（OK）を期待。
                .andExpect(jsonPath("$.id").value(1))  // JSONの"id"フィールドを検証
                .andExpect(jsonPath("$.message").value("Mocked Example 1")); // レスポンスJSONの"message"フィールドが期待通りであることを検証。

        // Serviceが1回だけ呼ばれたことを検証
        verify(exampleService, times(1)).getExampleById(testId);

        // Service2回以上呼ばれたことを検証
        //verify(exampleService, atLeast(2)).getExampleById(testId);

        //一度も呼び出されていない場合の検証：
        //verifyNoInteractions(exampleService);

        // サービスの特定メソッドが呼び出されていないことを検証
        //verify(exampleService, times(0)).getExampleById(any());

    }

    @Test
    void convertToTrainResponseDtoTest_1() throws Exception {

        // モックの設定
        Long testId = 1L;
        ExampleEntity mockResponse = new ExampleEntity();
        mockResponse.setId(testId);
        mockResponse.setMessage("Mocked Example " + testId);

        // モックされたexampleServiceのgetExampleByIdメソッドが呼ばれたとき、mockResponseを返すよう設定。
        when(exampleService.getExampleById(testId)).thenReturn(mockResponse);

        // テスト実行
        mockMvc.perform(get("/api/example/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())            // HTTPステータスコード200（OK）を期待。
                .andExpect(jsonPath("$.id").value(1))  // JSONの"id"フィールドを検証
                .andExpect(jsonPath("$.message").value("Mocked Example 1")); // レスポンスJSONの"message"フィールドが期待通りであることを検証。

        // Serviceが1回だけ呼ばれたことを検証
        verify(exampleService, times(1)).getExampleById(testId);

        // Service2回以上呼ばれたことを検証
        //verify(exampleService, atLeast(2)).getExampleById(testId);

        //一度も呼び出されていない場合の検証：
        //verifyNoInteractions(exampleService);

        // サービスの特定メソッドが呼び出されていないことを検証
        //verify(exampleService, times(0)).getExampleById(any());

    }

}
