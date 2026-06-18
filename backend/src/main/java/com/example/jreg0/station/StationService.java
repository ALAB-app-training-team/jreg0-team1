package com.example.jreg0.station;

import com.example.jreg0.stopstation.StopStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class StationService {
    private final StationRepository _stationRepository;
    private final StopStationRepository _stopStationRepository;

    @Autowired
    public StationService(StationRepository stationRepository, StopStationRepository stopStationRepository) {
        this._stationRepository = stationRepository;
        this._stopStationRepository = stopStationRepository;
    }

    public List<StationEntity> getAll() {
        return _stationRepository.findAll();
    }

    public List<StationEntity> getStationsOnSameRouteById(String stationId) {
        List<String> routeIdList = _stopStationRepository.findByIdStationId(stationId).stream().map(stopstation -> stopstation.getId().getRouteId()).toList();
        List<String> stationIdList = routeIdList.stream().flatMap(routeId -> _stopStationRepository.findByIdRouteId(routeId).stream().map(stopStationEntity -> stopStationEntity.getId().getStationId())).distinct().toList();
        List<StationEntity> stationEntityList = _stationRepository.findAllById(stationIdList);
        return stationEntityList;
    }
}

