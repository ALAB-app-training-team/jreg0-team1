package com.example.jreg0.route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "route")
public class RouteEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "route_name")
    private String routeName;
}

