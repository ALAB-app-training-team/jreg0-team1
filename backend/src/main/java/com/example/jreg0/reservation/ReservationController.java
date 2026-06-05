package com.example.jreg0.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {
    private ReservationService _service;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        _service = reservationService;
    }

    @PostMapping
    public ResponseEntity<String> ReserveSeat(@RequestBody ReservationDTO reservation) throws URISyntaxException {
        try {
            String reservationId = String.valueOf(_service.resgistReservation(reservation));
            URI location = new URI("https://tfha8bmmx5.ap-northeast-1.awsapprunner.com/reservation" + "reservationId");
            return ResponseEntity.created(location).body(reservationId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
