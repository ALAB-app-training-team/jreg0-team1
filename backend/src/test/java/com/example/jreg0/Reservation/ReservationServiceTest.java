package com.example.jreg0.Reservation;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.reservation.ReservationDetailResponse;
import com.example.jreg0.reservation.ReservationEntity;
import com.example.jreg0.reservation.ReservationRepository;
import com.example.jreg0.reservation.ReservationService;
import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.schedule.ScheduleRepository;
import com.example.jreg0.seat.SeatEntity;
import com.example.jreg0.seat.SeatRepository;
import com.example.jreg0.station.StationEntity;
import com.example.jreg0.station.StationRepository;
import com.example.jreg0.train.TrainEntity;
import com.example.jreg0.train.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private ReservationService reservationService;

    // モックの設定
    SeatEntity mockSeatforRegister = new SeatEntity();
    SeatEntity mockSeatforGet = new SeatEntity();

    ReservationEntity mockReservationforRegister = new ReservationEntity();
    ReservationEntity mockReservationforGet = new ReservationEntity();

    ScheduleEntity mockSchedule0 = new ScheduleEntity();
    ScheduleEntity mockSchedule1 = new ScheduleEntity();

    TrainEntity mockTrain0 = new TrainEntity();

    StationEntity mockDepartureStation = new StationEntity();
    StationEntity mockArrivalStation = new StationEntity();

    CarEntity mockCar = new CarEntity();

    UUID Id = UUID.randomUUID();

    @BeforeEach
    void setup() {
        //座席のデータセット
        mockSeatforRegister.setId("1A");

        //予約のデータセット
        mockReservationforGet.setId(Id);
        mockReservationforGet.setTrainId("00000000");
        mockReservationforGet.setDepartureStationId("00000000");
        mockReservationforGet.setArrivalStationId("00000001");
        mockReservationforGet.setSeatId("1A");
        mockReservationforGet.setDepartureDate(LocalDate.now());

        //列車のデータセット
        mockTrain0.setId("00000000");
        mockTrain0.setTrainName("Hayabusa");

        //駅のデータセット
        mockDepartureStation.setId("00000000");
        mockDepartureStation.setStationName("東京");
        mockArrivalStation.setId("00000001");
        mockArrivalStation.setStationName("上野");

        //号車のデータセット
        mockCar.setId("00000000");
        mockCar.setSeatType("Aザ");
        mockCar.setCarNumber(1);

        //席のデータセット
        mockSeatforGet.setId("1A");
        mockSeatforGet.setSeatLocation("1A");
        mockSeatforGet.setCar(mockCar);

        //スケジュールのデータセット
        mockSchedule0.setStationId("00000000");
        mockSchedule1.setStationId("00000001");
    }

    /**
     *予約登録処理を行い、UUIDを生成して返却する*
     */
    @Test
    void registerReservationTest_NormalCase_予約登録処理を実施しUUIDを生成して返却() {

        ReservationEntity savedReservation = new ReservationEntity();

        savedReservation.setId(Id);

        //座席のリポジトリモック定義
        when(seatRepository.findAll()).thenReturn(List.of(mockSeatforRegister));

        //予約のリポジトリモック定義
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(savedReservation);

        UUID resultId = reservationService.registerReservation(mockReservationforRegister);

        assertEquals(Id,resultId);
        assertEquals("1A", mockReservationforRegister.getSeatId());
        verify(seatRepository,times(1)).findAll();
        verify(reservationRepository,times(1)).save(mockReservationforRegister);
    }

    /**
     * 予約Idに一致する予約データを取得する*
     */
    @Test
    void checkReservationDetailTest_NormalCase_予約IDに一致する予約データを取得する() {
        //各種リポジトリモック定義
        when(reservationRepository.findById(Id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.of(mockDepartureStation));
        when(stationRepository.findById("00000001")).thenReturn(Optional.of(mockArrivalStation));
        when(seatRepository.findById("1A")).thenReturn(Optional.of(mockSeatforGet));
        when(scheduleRepository.findByTrainIdAndStationIdAndDepartureDate("00000000","00000000", LocalDate.now())).thenReturn(List.of(mockSchedule0));
        when(scheduleRepository.findByTrainIdAndStationIdAndDepartureDate("00000000","00000001", LocalDate.now())).thenReturn(List.of(mockSchedule1));

        ReservationDetailResponse result = reservationService.checkReservationDetail(Id.toString());

        assertNotNull(result);

        assertEquals(mockReservationforGet,result.getReservation());
        assertEquals(mockDepartureStation,result.getDepartureStation());
        assertEquals(mockArrivalStation,result.getArrivalStation());
        assertEquals(mockSeatforGet,result.getSeat());
        assertEquals(mockTrain0,result.getTrain());
        verify(reservationRepository).findById(Id);
    }


    /**
     * 予約Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_予約IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(Id)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->reservationService.checkReservationDetail(Id.toString()));
        assertAll(() -> assertEquals("予約が存在しません",exception.getMessage()));
    }

    /**
     * 列車Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_列車IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(Id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->reservationService.checkReservationDetail(Id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(列車)",exception.getMessage()));
    }

    /**
     * 駅Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_駅IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(Id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->reservationService.checkReservationDetail(Id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(駅)",exception.getMessage()));
    }

    /**
     * 座席Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_座席IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(Id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.of(mockDepartureStation));
        when(stationRepository.findById("00000001")).thenReturn(Optional.of(mockArrivalStation));
        when(seatRepository.findById("1A")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->reservationService.checkReservationDetail(Id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(座席・号車)",exception.getMessage()));
    }
}
