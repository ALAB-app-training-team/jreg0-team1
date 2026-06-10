package com.example.jreg0.schedule;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "station_id")
    private String stationId;

    @Column(name = "departure_time")
    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Column(name = "departure_track")
    private Integer departureTrack;

    @Column(name = "departure_date",columnDefinition = "DATE")
    private Date departureDate;

    @Column(name = "train_id")
    private String trainId;
}
