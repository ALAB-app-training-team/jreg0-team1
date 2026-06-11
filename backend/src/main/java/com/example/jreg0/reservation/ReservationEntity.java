package com.example.jreg0.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id")
    private UUID id;

    @Column(name = "seat_id")
    private String seatId;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "train_id")
    private String trainId;

    @Column(name = "departure_station_id")
    private String departureStationId;

    @Column(name = "arrival_station_id")
    private String arrivalStationId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "account_id")
    private String accountId;
}
