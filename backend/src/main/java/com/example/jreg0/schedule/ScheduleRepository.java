package com.example.jreg0.schedule;


import com.example.jreg0.stopstation.StopStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository <ScheduleEntity, String> {
    List<ScheduleEntity> findBySchedule (String trainId, Date departure_date);
}

