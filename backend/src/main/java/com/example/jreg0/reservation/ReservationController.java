package com.example.jreg0.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "reservations")
public class ReservationController {
    private final ReservationService _reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        _reservationService = reservationService;
    }

    @GetMapping(value = "{id}")
    public  ResponseEntity<ReservationDetailResponseDto> GetReservationDetailById(@PathVariable("id") String id){
        ReservationDetailResponse reservationDetailResponse = _reservationService.checkReservationDetail(id);
        ReservationDetailResponseDto reservationDetailResponseDto = new ReservationDetailResponseDto(
                reservationDetailResponse.getReservation().getId(),
                reservationDetailResponse.getReservation().getReservationDate(),
                reservationDetailResponse.getReservation().getDepartureDate(),
                reservationDetailResponse.getSeat().getSeatLocation(),
                reservationDetailResponse.getCar().getCarNumber(),
                reservationDetailResponse.getCar().getSeatType(),
                reservationDetailResponse.getDepartureStation().getStationName(),
                reservationDetailResponse.getScheduleByDepartureStation().getDepartureTime(),
                reservationDetailResponse.getArrivalStation().getStationName(),
                reservationDetailResponse.getScheduleByArrivalStation().getArrivalTime(),
                reservationDetailResponse.getScheduleByDepartureStation().getDepartureTrack(),
                reservationDetailResponse.getTrain().getTrainName(),
                reservationDetailResponse.getTrain().getTrainNickname()
        );
        return ResponseEntity.ok(reservationDetailResponseDto);
    }

    @PostMapping
    public ResponseEntity<String> ReserveSeat(@RequestBody ReservationRequestDto reservation) {
        try {
            String reservationId = String.valueOf(_reservationService.registerReservation(convertToReservationEntity(reservation)));
            URI location = new URI( "/reservations/"+ reservationId);
            return ResponseEntity.created(location).body(reservationId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 予約リクエストDto → 予約Entityの変換を行う
     *
     * @param reservationDto 予約リクエストDto
     * @return 予約Entity
     */
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
