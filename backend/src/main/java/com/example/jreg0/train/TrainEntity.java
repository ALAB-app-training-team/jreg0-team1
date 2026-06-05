package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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
    private String routeId;

    @Column(name = "train_nickname")
    private String train_nickname;

    @Column(name = "formation")
    private Integer formation;

    @ToString.Exclude
    @OneToMany(mappedBy = "train")
    private List<ScheduleEntity> schedules;
}
