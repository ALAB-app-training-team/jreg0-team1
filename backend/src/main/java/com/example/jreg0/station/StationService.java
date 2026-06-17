package com.example.jreg0.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private StationRepository _stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this._stationRepository = stationRepository;
    }

    public List<StationEntity> getAll() {
        return _stationRepository.findAll();
    }
}

