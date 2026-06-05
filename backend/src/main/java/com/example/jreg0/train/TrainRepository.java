package com.example.jreg0.train;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository <TrainEntity, String> {
    List<TrainEntity> findByRouteId(String routeId);
}

