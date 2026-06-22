package com.example.jreg0.schedule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository <ScheduleEntity, String> {
    List<ScheduleEntity> findByTrainIdAndStationIdAndDepartureDate(String trainId, String stationId, LocalDate departureDate);
}
