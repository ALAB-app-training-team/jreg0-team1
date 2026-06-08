package com.example.jreg0.stopstation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopStationRepository extends JpaRepository<StopStationEntity, StopStationIdEntity> {

    List<StopStationEntity> findByIdStationId(String stationId);
}

