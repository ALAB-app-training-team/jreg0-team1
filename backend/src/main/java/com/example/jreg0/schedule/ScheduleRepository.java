package com.example.jreg0.schedule;


import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
    List<ScheduleEntity> findByTrainIdAndStationIdAndDepartureDate(String trainId, String stationId, LocalDate departureDate);

    List<ScheduleEntity> findByTrainIdAndDepartureDate(String trainId, LocalDate departureDate);
}
