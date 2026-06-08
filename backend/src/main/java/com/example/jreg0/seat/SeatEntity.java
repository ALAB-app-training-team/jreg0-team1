package com.example.jreg0.seat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "seat")
public class SeatEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "car_id")
    private String carId;

    @Column(name = "seat_location")
    private String seatLocation;
}
