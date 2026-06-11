package com.example.jreg0.reservation;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.seat.SeatEntity;
import com.example.jreg0.station.StationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationDetailResponse {
    private ReservationEntity reservation;
    private StationEntity departureStation;
    private StationEntity arrivalStation;
    private SeatEntity seat;
    private CarEntity car;
    private ScheduleEntity scheduleByDepartureStation;
    private ScheduleEntity scheduleByArrivalStation;
}
