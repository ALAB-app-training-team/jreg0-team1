package com.example.jreg0.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
public class ScheduleResponseDto {
    private String Id;
    private String stationId;
    private Time departureTime;
    private Time arrivalTime;
    private Integer departureTrack;
    private Date departureDate;
}
