package com.example.jreg0.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
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
    private Date reservationDate;

    @Column(name = "train_id")
    private String trainId;

    @Column(name = "boarding_station_id")
    private String boardingStationId;

    @Column(name = "destination_station_id")
    private String destinationStationId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "account_id")
    private String accountId;
}
