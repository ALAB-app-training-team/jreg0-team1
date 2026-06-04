package com.example.jreg0.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "train_id")
    private String train_id;

    @Column(name = "station_id")
    private String station_id;

    @Column(name = "departure_time")
    private Time departure_time;

    @Column(name = "arrival_time")
    private Time arrival_time;

    @Column(name = "departure_track")
    private Integer departure_track;

    @Column(name = "departure_date")
    private Date departure_date;
}

