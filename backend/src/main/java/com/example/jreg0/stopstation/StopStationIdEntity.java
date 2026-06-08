package com.example.jreg0.stopstation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopStationIdEntity implements Serializable {
    @Column(name = "route_id")
    private String routeId;

    @Column(name = "station_id")
    private String stationId;
}
