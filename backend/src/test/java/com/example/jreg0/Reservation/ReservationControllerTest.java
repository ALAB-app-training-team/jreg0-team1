package com.example.jreg0.Reservation;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.reservation.*;
import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.seat.SeatEntity;
import com.example.jreg0.station.StationEntity;
import com.example.jreg0.train.TrainEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservationService reservationService;

    private ReservationDetailResponse mockResponse;

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
        //リクエストのモック
        mockRequest = new ReservationRequestDto();
        mockRequest.setSeatId("1A");
        mockRequest.setReservationDate(LocalDate.of(2026,06,10));
        mockRequest.setDepartureDate(LocalDate.of(2026,06,11));
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
    void getReservationDetailByIdTest_NomalCase1() throws Exception {
        //予約取得のサービスモック定義
        when(reservationService.checkReservationDetail(Id.toString())).thenReturn(mockResponse);

        mockMvc.perform(get("/reservations/{id}", Id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(Id.toString())).andExpect(jsonPath("$.trainName").value("Hayabusa"));
        verify(reservationService).checkReservationDetail(Id.toString());
    }

    /**
     * 予約情報を登録する*
     */
    @Test
    void reserveSeatTest_NomalCase1() throws Exception {
        //予約登録のサービスモック定義
        when(reservationService.registerReservation(convertToReservationEntity(mockRequest))).thenReturn(Id);

        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(mockRequest))).andExpect(status().isCreated());
        verify(reservationService).registerReservation(convertToReservationEntity(mockRequest));
    }

    private ReservationEntity convertToReservationEntity(ReservationRequestDto reservationDto) {
        ReservationEntity entity = new ReservationEntity();
        entity.setSeatId(reservationDto.getSeatId());
        entity.setReservationDate(reservationDto.getReservationDate());
        entity.setTrainId(reservationDto.getTrainId());
        entity.setDepartureStationId(reservationDto.getDepartureStationId());
        entity.setArrivalStationId(reservationDto.getArrivalStationId());
        entity.setPaymentMethod(reservationDto.getPaymentMethod());
        entity.setPaymentStatus(reservationDto.getPaymentStatus());
        entity.setFee(reservationDto.getFee());
        entity.setAccountId(reservationDto.getAccountId());

        return entity;
    }
}
