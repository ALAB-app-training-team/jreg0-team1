package com.example.jreg0.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReservationDetailResponseDto {
    private UUID id;
    private Date reservationDate;
    private Date departureDate;
    private String seatLocation;
    private Integer carNumber;
    private String seatType;
    private String departureStationName;
    private Time departureTimeByDepartureStation;
    private String arrivalStationName;
    private Time arrivalTimeByArrivalStation;
    private Integer departureTrackByDepartureStation;
}
