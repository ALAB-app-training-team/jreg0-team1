package com.example.jreg0.stopstation;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stopstation")
public class StopStationEntity {
    @EmbeddedId
    private StopStationIdEntity id;
}

