package com.example.jreg0.reservation;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping(path = "reservations")
public class ReservationController {
    private ReservationService _reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        _reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<String> ReserveSeat(@RequestBody ReservationRequestDto reservation) throws URISyntaxException {
        try {
            String reservationId = String.valueOf(_reservationService.resgisterReservation(convertToReservationEntity(reservation)));
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
        entity.setBoardingStationId(reservationDto.getBoardingStationId());
        entity.setDestinationStationId(reservationDto.getDestinationStationId());
        entity.setPaymentMethod(reservationDto.getPaymentMethod());
        entity.setPaymentStatus(reservationDto.getPaymentStatus());
        entity.setFee(reservationDto.getFee());
        entity.setAccountId(reservationDto.getAccountId());

        return entity;
    }
}
