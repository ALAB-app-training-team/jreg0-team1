package com.example.jreg0.stopstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopStationService {
    private StopStationRepository _stopStationRepository;

    @Autowired
    public void StopStationRepository(StopStationRepository stopstationRepository) {
        this._stopStationRepository = stopstationRepository;
    }

    public List<StopStationEntity> getAll(){
        return _stopStationRepository.findAll();
    }
}

