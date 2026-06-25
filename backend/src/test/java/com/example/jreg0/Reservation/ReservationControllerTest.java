package com.example.jreg0.Reservation;

import com.example.jreg0.car.*;
import com.example.jreg0.reservation.*;
import com.example.jreg0.schedule.*;
import com.example.jreg0.seat.*;
import com.example.jreg0.station.*;
import com.example.jreg0.train.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.webmvc.test.autoconfigure.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.web.servlet.*;
import tools.jackson.databind.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservationService reservationService;

    private ReservationDetailResponse mockResponse;

    private ReservationDetailResponseDto mockResponseDto;

    private ReservationRequestDto mockRequest;

    private UUID Id = UUID.randomUUID();

    // モックの設定
    ReservationEntity mockReservation = new ReservationEntity();

    SeatEntity mockSeat = new SeatEntity();

    CarEntity mockCar = new CarEntity();

    StationEntity mockDepartureStation = new StationEntity();
    StationEntity mockArrivalStation = new StationEntity();

    ScheduleEntity mockDepartureSchedule = new ScheduleEntity();
    ScheduleEntity mockArrivalSchedule1 = new ScheduleEntity();

    TrainEntity mockTrain = new TrainEntity();

    @BeforeEach
    void setup() {
        //予約のデータセット
        mockReservation.setId(Id);
        mockReservation.setReservationDate(LocalDate.of(2026, 6, 10));
        mockReservation.setDepartureDate(LocalDate.of(2026, 6, 11));
        //座席のデータセット
        mockSeat.setId("1A");
        //号車のデータセット
        mockCar.setCarNumber(1);
        mockCar.setSeatType("Aザ");
        //駅のデータセット
        mockDepartureStation.setStationName("東京");
        mockArrivalStation.setStationName("上野");
        //スケジュールのデータセット
        mockDepartureSchedule.setDepartureTime(new Time(9, 0, 0));
        mockDepartureSchedule.setDepartureTrack(1);
        mockArrivalSchedule1.setArrivalTime(new Time(9, 5, 0));
        //列車のデータセット
        mockTrain.setTrainName("Hayabusa");
        mockTrain.setTrainNickname("はやぶさ");

        //レスポンスのモック
        mockResponse = new ReservationDetailResponse(
                mockReservation, mockDepartureStation, mockArrivalStation, mockSeat, mockCar, mockDepartureSchedule, mockArrivalSchedule1, mockTrain
        );
        mockResponseDto = new ReservationDetailResponseDto(
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now(),
                "1A",
                1,
                "01",
                "上野",
                new Time(8, 43, 00),
                "仙台",
                new Time(10, 15, 00),
                20,
                "hayabusa-7",
                "はやぶさ"
        );
        //リクエストのモック
        mockRequest = new ReservationRequestDto();
        mockRequest.setSeatId("1A");
        mockRequest.setReservationDate(LocalDate.of(2026, 06, 10));
        mockRequest.setDepartureDate(LocalDate.of(2026, 06, 11));
        mockRequest.setTrainId("00000000");
        mockRequest.setDepartureStationId("00000000");
        mockRequest.setArrivalStationId("00000000");
        mockRequest.setPaymentMethod("01");
        mockRequest.setPaymentStatus("01");
        mockRequest.setFee(12000);
        mockRequest.setAccountId(Id.toString());
    }

    /**
     * Idが一致する予約情報を取得する*
     */
    @Test
    void getReservationDetailByIdTest_NormalCase_予約IDが一致する予約情報を取得する() throws Exception {
        //予約取得のサービスモック定義
        when(reservationService.getReservationDetail(Id.toString())).thenReturn(mockResponse);

        mockMvc.perform(get("/reservations/{id}", Id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(Id.toString())).andExpect(jsonPath("$.trainName").value("Hayabusa"));
        verify(reservationService).getReservationDetail(Id.toString());
    }

    /**
     * 予約情報を登録する*
     */
    @Test
    void reserveSeatTest_NormalCase_予約情報を登録する() throws Exception {
        //予約登録のサービスモック定義
        when(reservationService.registerReservation(any())).thenReturn(Id);

        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(mockRequest))).andExpect(status().isCreated());
        verify(reservationService).registerReservation(any());
    }

    @Test
    void deleteAllReservations_NormalCase_予約情報を全削除する() throws Exception {
        mockMvc.perform(delete("/reservations")).andExpect(status().isNoContent());
        verify(reservationService).deleteAllReservations();
    }

    @Nested
    class getReservationListTest {
        /**
         * 予約情報一覧を登録する
         */
        @Test
        void getReservationList_NormalCase_予約一覧を取得する() throws Exception {
            when(reservationService.getReservationList()).thenReturn(List.of(mockResponseDto));

            mockMvc.perform(get("/reservations")).andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].departureStationName").value("上野"))
                    .andExpect(jsonPath("$[0].arrivalStationName").value("仙台"))
                    .andExpect(jsonPath("$[0].seatLocation").value("1A"))
                    .andExpect(jsonPath("$[0].carNumber").value(1))
                    .andExpect(jsonPath("$[0].seatType").value("01"))
                    .andExpect(jsonPath("$[0].departureTime").value("08:43:00"))
                    .andExpect(jsonPath("$[0].arrivalTime").value("10:15:00"))
                    .andExpect(jsonPath("$[0].departureTrack").value(20))
                    .andExpect(jsonPath("$[0].trainName").value("hayabusa-7"))
                    .andExpect(jsonPath("$[0].trainNickname").value("はやぶさ"));

            verify(reservationService).getReservationList();
        }

        /**
         * 予約がない場合は空のリストを取得する
         */
        @Test
        void getReservationList_NormalCase_予約がない場合は空のリストを取得する() throws Exception {
            when(reservationService.getReservationList()).thenReturn(List.of());

            mockMvc.perform(get("/reservations"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));
            verify(reservationService).getReservationList();
        }

        @Test
        void getReservationList_ErrorCase_サービスの例外をキャッチしてInternalServerErrorを取得する() throws Exception {
            when(reservationService.getReservationList()).thenThrow(new RuntimeException());

            mockMvc.perform(get("/reservations"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().string("取得に失敗しました"));
            verify(reservationService).getReservationList();
        }
    }
}
