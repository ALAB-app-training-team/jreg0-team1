package com.example.jreg0.station;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "stations")
public class StationController {
    private final StationService _stationService;

    public StationController(StationService stationService){
        _stationService = stationService;
    }

    @GetMapping
    public List<StationEntity> getStations(){
        List<StationEntity> stationEntities = _stationService.getAll();

        return stationEntities;
    }
}
