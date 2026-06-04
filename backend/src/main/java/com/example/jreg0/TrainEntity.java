package com.example.jreg0;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "train")
public class TrainEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "train_number")
    private String train_number;

    @Column(name = "train_name")
    private String train_name;

    @Column(name = "route_id")
    private String route_id;

    @Column(name = "train_nickname")
    private String train_nickname;

    @Column(name = "formation")
    private Integer formation;
}
