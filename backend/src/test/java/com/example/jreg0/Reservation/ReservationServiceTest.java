package com.example.jreg0.Reservation;

import com.example.jreg0.car.*;
import com.example.jreg0.reservation.*;
import com.example.jreg0.schedule.*;
import com.example.jreg0.seat.*;
import com.example.jreg0.station.*;
import com.example.jreg0.train.*;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    SeatEntity mockSeatforGet2 = new SeatEntity();

    ReservationEntity mockReservationforRegister = new ReservationEntity();
    ReservationEntity mockReservationforGet = new ReservationEntity();
    ReservationEntity mockReservationforGet2 = new ReservationEntity();

    ScheduleEntity mockSchedule0 = new ScheduleEntity();
    ScheduleEntity mockSchedule1 = new ScheduleEntity();

    TrainEntity mockTrain0 = new TrainEntity();

    StationEntity mockDepartureStation = new StationEntity();
    StationEntity mockArrivalStation = new StationEntity();

    CarEntity mockCar = new CarEntity();

    UUID id = UUID.randomUUID();

    @BeforeEach
    void setup() {
        //座席のデータセット
        mockSeatforRegister.setId("1A");

        //予約のデータセット
        mockReservationforGet.setId(id);
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
        mockSchedule0.setDepartureTime(new Time(6, 45, 00));
        mockSchedule0.setArrivalTime(new Time(6, 45, 00));
        mockSchedule0.setDepartureTrack(1);

        mockSchedule1.setStationId("00000001");
        mockSchedule0.setDepartureTime(new Time(6, 48, 00));
        mockSchedule0.setArrivalTime(new Time(6, 48, 00));
        mockSchedule0.setDepartureTrack(1);
    }

    /**
     * 予約登録処理を行い、UUIDを生成して返却する*
     */
    @Test
    void registerReservationTest_NormalCase_予約登録処理を実施しUUIDを生成して返却() {

        ReservationEntity savedReservation = new ReservationEntity();

        savedReservation.setId(id);

        //座席のリポジトリモック定義
        when(seatRepository.findAll()).thenReturn(List.of(mockSeatforRegister));

        //予約のリポジトリモック定義
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(savedReservation);

        UUID resultId = reservationService.registerReservation(mockReservationforRegister);

        assertEquals(id, resultId);
        assertEquals("1A", mockReservationforRegister.getSeatId());
        verify(seatRepository, times(1)).findAll();
        verify(reservationRepository, times(1)).save(mockReservationforRegister);
    }

    /**
     * 予約Idに一致する予約データを取得する*
     */
    @Test
    void checkReservationDetailTest_NormalCase_予約IDに一致する予約データを取得する() {
        //各種リポジトリモック定義
        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.of(mockDepartureStation));
        when(stationRepository.findById("00000001")).thenReturn(Optional.of(mockArrivalStation));
        when(seatRepository.findById("1A")).thenReturn(Optional.of(mockSeatforGet));
        when(scheduleRepository.findByTrainIdAndStationIdAndDepartureDate("00000000", "00000000", LocalDate.now())).thenReturn(List.of(mockSchedule0));
        when(scheduleRepository.findByTrainIdAndStationIdAndDepartureDate("00000000", "00000001", LocalDate.now())).thenReturn(List.of(mockSchedule1));

        ReservationDetailResponse result = reservationService.checkReservationDetail(id.toString());

        assertNotNull(result);

        assertEquals(mockReservationforGet, result.getReservation());
        assertEquals(mockDepartureStation, result.getDepartureStation());
        assertEquals(mockArrivalStation, result.getArrivalStation());
        assertEquals(mockSeatforGet, result.getSeat());
        assertEquals(mockTrain0, result.getTrain());
        verify(reservationRepository).findById(id);
    }


    /**
     * 予約Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_予約IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reservationService.checkReservationDetail(id.toString()));
        assertAll(() -> assertEquals("予約が存在しません", exception.getMessage()));
    }

    /**
     * 列車Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_列車IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reservationService.checkReservationDetail(id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(列車)", exception.getMessage()));
    }

    /**
     * 駅Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_駅IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reservationService.checkReservationDetail(id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(駅)", exception.getMessage()));
    }

    /**
     * 座席Idに一致するレコードがない場合はEntityNotFoundExceptionを返す*
     */
    @Test
    void checkReservationDetailTest_ErrorCase_座席IDに一致するレコードがない場合はEntityNotFoundExceptionを返す() {
        //リポジトリモック定義
        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservationforGet));
        when(trainRepository.findById("00000000")).thenReturn(Optional.of(mockTrain0));
        when(stationRepository.findById("00000000")).thenReturn(Optional.of(mockDepartureStation));
        when(stationRepository.findById("00000001")).thenReturn(Optional.of(mockArrivalStation));
        when(seatRepository.findById("1A")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reservationService.checkReservationDetail(id.toString()));
        assertAll(() -> assertEquals("予約に紐づくデータが存在しません(座席・号車)", exception.getMessage()));
    }

    @Nested
    class checkReservationListTest {
        @BeforeEach
        void setUp() {
            mockSeatforGet.setCar(mockCar);
            mockReservationforGet.setDepartureStation(mockDepartureStation);
            mockReservationforGet.setArrivalStation(mockArrivalStation);
            mockReservationforGet.setSeat(mockSeatforGet);
            mockReservationforGet.setDepartureSchedule(mockSchedule0);
            mockReservationforGet.setArrivalSchedule(mockSchedule1);
            mockReservationforGet.setTrain(mockTrain0);
        }

        @Test
        void checkReservationList_NormalCase_正しく予約データを取得できる() {
            when(reservationRepository.findAll()).thenReturn(List.of(mockReservationforGet));
            List<ReservationDetailResponseDto> result = reservationService.checkReservationList();
            assertAll(() -> {
                assertEquals(mockReservationforGet.getId(), result.getFirst().getId());
                assertEquals(mockReservationforGet.getReservationDate(), result.getFirst().getReservationDate());
                assertEquals(mockReservationforGet.getDepartureDate(), result.getFirst().getDepartureDate());
                assertEquals(mockReservationforGet.getSeat().getSeatLocation(), result.getFirst().getSeatLocation());
                assertEquals(mockReservationforGet.getSeat().getCar().getCarNumber(), result.getFirst().getCarNumber());
                assertEquals(mockReservationforGet.getSeat().getCar().getSeatType(), result.getFirst().getSeatType());
                assertEquals(mockReservationforGet.getDepartureStation().getStationName(), result.getFirst().getDepartureStationName());
                assertEquals(mockReservationforGet.getDepartureSchedule().getDepartureTime(), result.getFirst().getDepartureTime());
                assertEquals(mockReservationforGet.getArrivalStation().getStationName(), result.getFirst().getArrivalStationName());
                assertEquals(mockReservationforGet.getArrivalSchedule().getArrivalTime(), result.getFirst().getArrivalTime());
                assertEquals(mockReservationforGet.getDepartureSchedule().getDepartureTrack(), result.getFirst().getDepartureTrack());
                assertEquals(mockReservationforGet.getTrain().getTrainName(), result.getFirst().getTrainName());
                assertEquals(mockReservationforGet.getTrain().getTrainNickname(), result.getFirst().getTrainNickname());
            });
        }

        @Test
        void checkReservationList_NormalCase_予約データ複数件取得できる() {
            mockSeatforGet2.setCar(mockCar);
            mockReservationforGet2.setDepartureStation(mockDepartureStation);
            mockReservationforGet2.setArrivalStation(mockArrivalStation);
            mockReservationforGet2.setSeat(mockSeatforGet2);
            mockReservationforGet2.setDepartureSchedule(mockSchedule0);
            mockReservationforGet2.setArrivalSchedule(mockSchedule1);
            mockReservationforGet2.setTrain(mockTrain0);

            when(reservationRepository.findAll()).thenReturn(List.of(mockReservationforGet, mockReservationforGet2));

            assertEquals(2, reservationService.checkReservationList().size());
        }

        @Test
        void checkReservationList_NormalCase_予約が存在しない場合空の配列を返す() {
            List<ReservationEntity> mockEnptyReservationList = List.of();
            when(reservationRepository.findAll()).thenReturn(mockEnptyReservationList);

            assertTrue(reservationService.checkReservationList().isEmpty());
        }

        @Test
        void checkReservationList_NormalCase_予約は存在するのに予約に紐づく情報が存在しないときExceptionを返す() {
            mockReservationforGet.setSeat(null);
            when(reservationRepository.findAll()).thenReturn(List.of(mockReservationforGet));

            assertThrows(RuntimeException.class, () -> reservationService.checkReservationList());
        }
    }
}
