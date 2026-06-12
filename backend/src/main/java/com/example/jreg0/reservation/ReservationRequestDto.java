package com.example.jreg0.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {
    private String seatId;
    private LocalDate reservationDate;
    private LocalDate departureDate;
    private String trainId;
    private String departureStationId;
    private String arrivalStationId;
    private String paymentMethod;
    private String paymentStatus;
    private Integer fee;
    private String accountId;
}
