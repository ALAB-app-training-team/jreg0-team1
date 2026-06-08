package com.example.jreg0.station;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "station")
public class StationEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "station_name")
    private String stationName;
}

