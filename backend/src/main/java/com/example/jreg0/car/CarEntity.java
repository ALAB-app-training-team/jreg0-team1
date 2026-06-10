package com.example.jreg0.car;

import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.seat.SeatEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "train_id")
    private String trainId;

    @Column(name = "car_number")
    private Integer carNumber;

    @Column(name = "seat_type")
    private String seatType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seat")
    private List<SeatEntity> seats;
}

