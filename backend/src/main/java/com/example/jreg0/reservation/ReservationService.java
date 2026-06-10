package com.example.jreg0.reservation;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.car.CarRepository;
import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.schedule.ScheduleRepository;
import com.example.jreg0.seat.SeatEntity;
import com.example.jreg0.seat.SeatRepository;
import com.example.jreg0.station.StationEntity;
import com.example.jreg0.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository _reservationRepository;
    private final SeatRepository _seatRepository;
    private final StationRepository _stationRepository;
    private final CarRepository _carRepository;
    private final ScheduleRepository _scheduleRepository;

    @Autowired
    public ReservationService(
            ReservationRepository reservationRepository,
            SeatRepository seatRepository,
            StationRepository stationRepository,
            CarRepository carRepository,
            ScheduleRepository scheduleRepository
    ) {
        _reservationRepository = reservationRepository;
        _seatRepository = seatRepository;
        _stationRepository = stationRepository;
        _carRepository = carRepository;
        _scheduleRepository = scheduleRepository;
    }

    /**
     * 予約を登録するメソッド
     *
     * @param reservation id以外の予約情報の入った予約Entity
     * @return UUID 登録された予約の予約ID (UUID)
     */
    @Transactional
    public UUID registerReservation(ReservationEntity reservation) {
        // 今回は,座席を1Aに絞る
        reservation.setSeatId(_seatRepository.findAll().getFirst().getId());
        ReservationEntity saved = _reservationRepository.save(reservation);
        return saved.getId();
    }

    /**
     * 予約idと一致する予約詳細を取得するメソッド
     *
     * @param id 予約Entityのid
     * @return ReservationDetailResponse idと一致する予約詳細
     */
    public ReservationDetailResponse checkReservationDetail(String id){
        Optional<ReservationEntity> optionalReservation = _reservationRepository.findById(UUID.fromString(id));

        ReservationEntity reservation = optionalReservation.orElseThrow(() -> new IllegalArgumentException("予約ないよ"));

        // 出発駅、降車駅
        Optional<StationEntity> optionalDepartureStation = _stationRepository.findById(reservation.getDepartureStationId());
        StationEntity departureStation = optionalDepartureStation.orElseThrow(() -> new IllegalArgumentException("列車がないよ"));
        Optional<StationEntity> optionalArrivalStation = _stationRepository.findById(reservation.getArrivalStationId());
        StationEntity arrivalStation = optionalArrivalStation.orElseThrow(() -> new IllegalArgumentException("列車がないよ"));

        // 座席
        Optional<SeatEntity> optionalSeat = _seatRepository.findById(reservation.getSeatId());
        SeatEntity seat = optionalSeat.orElseThrow(() -> new IllegalArgumentException("シートないよ"));
        // 号車
        Optional<CarEntity> optionalCar = _carRepository.findById(seat.getCarId());
        CarEntity car = optionalCar.orElseThrow(() -> new IllegalArgumentException("号車がないよ"));

        // 出発時間、出発ホーム
        Optional<ScheduleEntity> optionalDepartureSchedule = _scheduleRepository.findByTrainIdAndStationId(reservation.getTrainId(), departureStation.getId());
        ScheduleEntity departureSchedule = optionalDepartureSchedule.orElseThrow(() -> new IllegalArgumentException("時刻表がないよ"));
        // 到着時間
        Optional<ScheduleEntity> optionalArrivalSchedule = _scheduleRepository.findByTrainIdAndStationId(reservation.getTrainId(), arrivalStation.getId());
        ScheduleEntity arrivalSchedule = optionalArrivalSchedule.orElseThrow(() -> new IllegalArgumentException("時刻表がないよ"));

        ReservationDetailResponse reservationDetailResponse = new ReservationDetailResponse(
                reservation,
                departureStation,
                arrivalStation,
                seat,
                car,
                departureSchedule,
                arrivalSchedule
        );
        return reservationDetailResponse;
    }
}
