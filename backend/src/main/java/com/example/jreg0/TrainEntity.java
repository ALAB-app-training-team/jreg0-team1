package com.example.jreg0;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "train")
public class TrainEntity {

    @Column(name = "id")
    private String id;

    @Column(name = "train_number")
    private String train_number;

    @Column(name = "train_name")
    private String train_name;
}
