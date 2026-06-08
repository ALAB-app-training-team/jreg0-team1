package com.example.jreg0.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequestDto {
    private String seatId;
    private Date reservationDate;
    private String trainId;
    private String boardingStationId;
    private String destinationStationId;
    private String paymentMethod;
    private String paymentStatus;
    private Integer fee;
    private String accountId;
}
