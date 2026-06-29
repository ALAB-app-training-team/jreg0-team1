package com.example.jreg0.Util;

import com.example.jreg0.schedule.*;
import com.example.jreg0.train.*;
import com.example.jreg0.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StopStationListFactoryTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private StopStationListFactory factory;

    @Test
    void Hello_test() {
        assertEquals(2, 1 + 1);
    }

    private LocalDate today = LocalDate.now();
    ScheduleEntity mockSchedule0 = new ScheduleEntity();
    ScheduleEntity mockSchedule1 = new ScheduleEntity();
    ScheduleEntity mockSchedule2 = new ScheduleEntity();
    ScheduleEntity mockSchedule3 = new ScheduleEntity();
    TrainEntity mockTrain = new TrainEntity();

    @BeforeEach
    void setup() {
        mockTrain.setId("HYB001");

        mockSchedule0.setId("00000000");
        mockSchedule1.setId("00000001");
        mockSchedule2.setId("00000002");
        mockSchedule3.setId("00000003");

        mockSchedule0.setTrain(mockTrain);

        mockSchedule0.setStationId("TKY01");
        mockSchedule1.setStationId("UEN02");
        mockSchedule2.setStationId("OMY03");
        mockSchedule3.setStationId("SND11");

        mockSchedule0.setDepartureDate(today);
        mockSchedule1.setDepartureDate(today);
        mockSchedule2.setDepartureDate(today);
        mockSchedule3.setDepartureDate(today);

        mockSchedule0.setDepartureTime(new Time(15, 10, 00));
        mockSchedule1.setDepartureTime(new Time(15, 25, 00));
        mockSchedule2.setDepartureTime(new Time(15, 35, 00));

        when(scheduleRepository.findByTrainIdAndDepartureDate("HYB001", today)).thenReturn(List.of(mockSchedule0, mockSchedule2, mockSchedule1, mockSchedule3));
    }

    @Test
    void create_発車時刻の昇順でリストを作成() {
        StopStationList result = factory.create("HYB001", today);

        assertThat(result.getList()).containsExactly("TKY01", "UEN02", "OMY03", "SND11");
    }

    @Test
    void create_該当データがなければ空のリストを返す() {
        when(scheduleRepository.findByTrainIdAndDepartureDate("NODATA", today)).thenReturn(List.of());

        StopStationList result = factory.create("NODATA", today);

        assertThat(result.getList()).isEmpty();
    }
}
