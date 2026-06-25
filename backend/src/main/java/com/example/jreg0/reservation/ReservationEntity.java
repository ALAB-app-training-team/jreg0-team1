package com.example.jreg0.reservation;

import com.example.jreg0.schedule.*;
import com.example.jreg0.seat.*;
import com.example.jreg0.station.*;
import com.example.jreg0.train.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StationEntity departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StationEntity arrivalStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SeatEntity seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "train_id", referencedColumnName = "train_id", insertable = false, updatable = false),
            @JoinColumn(name = "departure_date", referencedColumnName = "departure_date", insertable = false, updatable = false),
            @JoinColumn(name = "departure_station_id", referencedColumnName = "station_id", insertable = false, updatable = false)
    })
    private ScheduleEntity departureSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "train_id", referencedColumnName = "train_id", insertable = false, updatable = false),
            @JoinColumn(name = "departure_date", referencedColumnName = "departure_date", insertable = false, updatable = false),
            @JoinColumn(name = "arrival_station_id", referencedColumnName = "station_id", insertable = false, updatable = false)
    })
    private ScheduleEntity arrivalSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TrainEntity train;

}
