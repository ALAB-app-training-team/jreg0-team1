package com.example.jreg0.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private StationRepository _repository;

    @Autowired
    public void StationRepository(StationRepository stationRepository) {
        this._repository = stationRepository;
    }

    public List<StationEntity> getAll() {
        return _repository.findAll();
    }
}

