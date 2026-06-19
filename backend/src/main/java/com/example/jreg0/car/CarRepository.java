package com.example.jreg0.car;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<CarEntity, String> {
    @EntityGraph(attributePaths = {"seats"})
    List<CarEntity> findByTrainId(String trainId);
}
