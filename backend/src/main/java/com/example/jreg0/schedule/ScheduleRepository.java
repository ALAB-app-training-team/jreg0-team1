package com.example.jreg0.schedule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository <ScheduleEntity, String> {

    Optional<ScheduleEntity> findByTrainIdAndStationId(String trainId, String stationId);
}

