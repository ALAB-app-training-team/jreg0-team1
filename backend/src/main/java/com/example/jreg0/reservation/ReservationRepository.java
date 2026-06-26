package com.example.jreg0.reservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    @EntityGraph(attributePaths = {"departureStation", "arrivalStation", "seat", "departureSchedule", "train"})
    List<ReservationEntity> findAll();
}
