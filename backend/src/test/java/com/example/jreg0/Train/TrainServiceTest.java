package com.example.jreg0.Train;

import com.example.jreg0.route.RouteEntity;
import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.schedule.ScheduleRepository;
import com.example.jreg0.station.StationEntity;
import com.example.jreg0.stopstation.StopStationEntity;
import com.example.jreg0.stopstation.StopStationIdEntity;
import com.example.jreg0.stopstation.StopStationRepository;
import com.example.jreg0.train.TrainEntity;
import com.example.jreg0.train.TrainRepository;
import com.example.jreg0.train.TrainService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {
    @Mock
    private TrainRepository trainRepository;

    @Mock
    private StopStationRepository stopStationRepository;

    @InjectMocks
    private TrainService trainService;

    // モックの設定
    List<ScheduleEntity> mockScheduleList = new ArrayList<ScheduleEntity>();

    ScheduleEntity mockSchedule0 = new ScheduleEntity();
    ScheduleEntity mockSchedule1 = new ScheduleEntity();

    TrainEntity mockTrain0 = new TrainEntity();

    StopStationEntity mockStopStation0 = new StopStationEntity();
    StopStationEntity mockStopStation1 = new StopStationEntity();
    StopStationIdEntity mockStopStationId0 = new StopStationIdEntity();
    StopStationIdEntity mockStopStationId1 = new StopStationIdEntity();

    @BeforeEach
    void setup() {
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

        //停車駅のデータセット
        mockStopStationId0.setRouteId("00000000");
        mockStopStationId0.setStationId("00000000");
        mockStopStationId1.setRouteId("00000000");
        mockStopStationId1.setStationId("00000001");
        mockStopStation0.setId(mockStopStationId0);
        mockStopStation1.setId(mockStopStationId1);
    }

    /**
     * 出発駅・到着駅・出発日に一致する列車が存在する場合該当列車を取得する。*
     */
    @Test
    void getTrainByStationTest_NomalCase1() {
        //停車駅のリポジトリモック定義
        when(stopStationRepository.findByIdStationId("00000000")).thenReturn(List.of(mockStopStation0));

        when(stopStationRepository.findByIdStationId("00000001")).thenReturn(List.of(mockStopStation1));

        //列車のリポジトリモック定義
        when(trainRepository.findByRouteId("00000000")).thenReturn(List.of(mockTrain0));

        List<TrainEntity> result = trainService.getTrainByStation("00000000", "00000001", new Date(2026, 6, 3));

        assertEquals(1, result.size());
        assertEquals(mockTrain0.getId(), result.getFirst().getId());

    }

    /**
     * 出発駅・到着駅・出発日に一致する列車が存在しない場合結果は0件になる*
     */
    @Test
    void getTrainByStationTest_NomalCase2() {
        //停車駅のリポジトリモック定義
        when(stopStationRepository.findByIdStationId("00000000")).thenReturn(List.of(mockStopStation0));

        when(stopStationRepository.findByIdStationId("00000001")).thenReturn(List.of(mockStopStation1));

        //列車のリポジトリモック定義
        when(trainRepository.findByRouteId("00000000")).thenReturn(List.of(mockTrain0));

        List<TrainEntity> result1 = trainService.getTrainByStation("00000000", "00000001", new Date(2026, 6, 4));

        List<TrainEntity> result2 = trainService.getTrainByStation("00000001", "00000000", new Date(2026, 6, 3));

        assertEquals(0, result1.size());
        assertEquals(0, result2.size());
    }

}
