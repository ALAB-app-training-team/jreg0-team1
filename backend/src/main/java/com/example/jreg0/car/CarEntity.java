package com.example.jreg0.car;

import jakarta.persistence.*;
import lombok.Data;

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

}

