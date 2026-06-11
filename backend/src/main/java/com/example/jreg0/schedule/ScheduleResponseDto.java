package com.example.jreg0.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ScheduleResponseDto {
    private String id;
    private String stationId;
    private Time departureTime;
    private Time arrivalTime;
    private Integer departureTrack;
    private LocalDate departureDate;
}
