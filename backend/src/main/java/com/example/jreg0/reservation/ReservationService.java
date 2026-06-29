package com.example.jreg0.reservation;

import com.example.jreg0.schedule.*;
import com.example.jreg0.seat.*;
import com.example.jreg0.station.*;
import com.example.jreg0.train.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
public class ReservationService {
    private final ReservationRepository _reservationRepository;
    private final SeatRepository _seatRepository;
    private final StationRepository _stationRepository;
    private final ScheduleRepository _scheduleRepository;
    private final TrainRepository _trainRepository;

    @Autowired
    public ReservationService(
            ReservationRepository reservationRepository,
            SeatRepository seatRepository,
            StationRepository stationRepository,
            ScheduleRepository scheduleRepository,
            TrainRepository trainRepository
    ) {
        _reservationRepository = reservationRepository;
        _seatRepository = seatRepository;
        _stationRepository = stationRepository;
        _scheduleRepository = scheduleRepository;
        _trainRepository = trainRepository;
    }

    /**
     * 予約を登録するメソッド
     *
     * @param reservation id以外の予約情報の入った予約Entity
     * @return UUID 登録された予約の予約ID (UUID)
     */
    @Transactional
    public UUID registerReservation(ReservationEntity reservation) {
        ReservationEntity saved = _reservationRepository.save(reservation);
        return saved.getId();
    }

    /**
     * 予約idと一致する予約詳細を取得するメソッド
     *
     * @param id 予約Entityのid
     * @return ReservationDetailResponse idと一致する予約詳細
     */
    public ReservationDetailResponse getReservationDetail(String id) {
        UUID reservationId = UUID.fromString(id);
        Optional<ReservationEntity> optionalReservation = _reservationRepository.findById(reservationId);
        ReservationEntity reservation = optionalReservation.orElseThrow(() -> new EntityNotFoundException("予約が存在しません"));

        Optional<TrainEntity> optionalTrain = _trainRepository.findById(reservation.getTrainId());
        TrainEntity train = optionalTrain.orElseThrow(() -> new EntityNotFoundException("予約に紐づくデータが存在しません(列車)"));

        Optional<StationEntity> optionalDepartureStation = _stationRepository.findById(reservation.getDepartureStationId());
        StationEntity departureStation = optionalDepartureStation.orElseThrow(() -> new EntityNotFoundException("予約に紐づくデータが存在しません(駅)"));
        Optional<StationEntity> optionalArrivalStation = _stationRepository.findById(reservation.getArrivalStationId());
        StationEntity arrivalStation = optionalArrivalStation.orElseThrow(() -> new EntityNotFoundException("予約に紐づくデータが存在しません(駅)"));

        Optional<SeatEntity> optionalSeat = _seatRepository.findById(reservation.getSeatId());
        SeatEntity seat = optionalSeat.orElseThrow(() -> new EntityNotFoundException("予約に紐づくデータが存在しません(座席・号車)"));

        List<ScheduleEntity> optionalDepartureSchedule = _scheduleRepository.findByTrainIdAndStationIdAndDepartureDate(reservation.getTrainId(), departureStation.getId(), reservation.getDepartureDate());
        ScheduleEntity departureSchedule = optionalDepartureSchedule.getFirst();
        List<ScheduleEntity> optionalArrivalSchedule = _scheduleRepository.findByTrainIdAndStationIdAndDepartureDate(reservation.getTrainId(), arrivalStation.getId(), reservation.getDepartureDate());
        ScheduleEntity arrivalSchedule = optionalArrivalSchedule.getFirst();

        ReservationDetailResponse reservationDetailResponse = new ReservationDetailResponse(
                reservation,
                departureStation,
                arrivalStation,
                seat,
                seat.getCar(),
                departureSchedule,
                arrivalSchedule,
                train
        );
        return reservationDetailResponse;
    }

    /**
     * 予約を全削除するメソッド
     */
    @Transactional
    public void deleteAllReservations() {
        _reservationRepository.deleteAll();
    }

    /**
     * 予約情報をすべて取得するメソッド
     *
     * @return List<ReservationDetailResponseDto> 予約情報の一覧すべて
     */
    public List<ReservationDetailResponseDto> getReservationList() {
        List<ReservationEntity> fetchedReservList = _reservationRepository.findAll();
        List<ReservationDetailResponseDto> dtoList = fetchedReservList.stream().map(reservation ->
                new ReservationDetailResponseDto(
                        reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getDepartureDate(),
                        reservation.getSeat().getSeatLocation(),
                        reservation.getSeat().getCar().getCarNumber(),
                        reservation.getSeat().getCar().getSeatType(),
                        reservation.getDepartureStation().getStationName(),
                        reservation.getDepartureSchedule().getDepartureTime(),
                        reservation.getArrivalStation().getStationName(),
                        reservation.getArrivalSchedule().getArrivalTime(),
                        reservation.getDepartureSchedule().getDepartureTrack(),
                        reservation.getTrain().getTrainName(),
                        reservation.getTrain().getTrainNickname()
                )
        ).collect(Collectors.toList());

        return dtoList;
    }
}
