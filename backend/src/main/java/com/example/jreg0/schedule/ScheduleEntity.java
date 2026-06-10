package com.example.jreg0.schedule;

import com.example.jreg0.train.TrainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    private TrainEntity train;
}
