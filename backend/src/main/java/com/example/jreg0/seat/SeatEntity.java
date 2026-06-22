package com.example.jreg0.seat;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.train.TrainEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "seat")
public class SeatEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "seat_location")
    private String seatLocation;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CarEntity car;
}
