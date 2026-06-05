package com.example.jreg0.schedule;

import com.example.jreg0.train.TrainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "station_id")
    private String station_id;

    @Column(name = "departure_time")
    private Time departure_time;

    @Column(name = "arrival_time")
    private Time arrival_time;

    @Column(name = "departure_track")
    private Integer departure_track;

    @Column(name = "departure_date",columnDefinition = "DATE")
    private Date departure_date;

    // ManyToOne, JoinColumnのアノテーションが必要
    // JoinColumn の name : テーブルを関連付けるカラム名
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "train_id")
    private TrainEntity train;
}

