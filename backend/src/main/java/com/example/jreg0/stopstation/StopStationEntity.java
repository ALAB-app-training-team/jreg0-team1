package com.example.jreg0.stopstation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stopstation")
public class StopStationEntity {
    @Id
    @Column(name = "route_id")
    private String route_id;

    @Column(name = "station_id")
    private String station_id;
}
