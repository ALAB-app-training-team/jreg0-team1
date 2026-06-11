package com.example.jreg0.seat;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, String > {
    @EntityGraph(attributePaths = {"car"})
    Optional<SeatEntity> findById(String id);
}
