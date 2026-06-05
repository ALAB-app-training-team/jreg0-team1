package com.example.jreg0.train;

import com.example.jreg0.stopstation.StopStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainService {
    private TrainRepository _train_repository;
    private StopStationRepository _stopStation_repository;

    @Autowired
    public TrainService(TrainRepository trainRepository, StopStationRepository stopStationRepository) {
        this._train_repository = trainRepository;
        this._stopStation_repository = stopStationRepository;
    }

    public List<TrainEntity> getAll() {
        return _train_repository.findAll();
    }

    private List<String> getRouteByStation(String boardingStationId, String destinationStationId) {
//        Set<String> routeByBoardingStationSet = (Set<String>) _stopStation_repository.findByStationId(boardingStationId).stream().map(stopstation -> stopstation.getRouteId()).toList();
//        Set<String> routeByDestinationStationSet = (Set<String>) _stopStation_repository.findByStationId(destinationStationId).stream().map(stopstation -> stopstation.getRouteId()).toList();
        List<String> routeByBoardingStationList = _stopStation_repository.findByStationId(boardingStationId).stream().map(stopstation -> stopstation.getRouteId()).toList();
        List<String> routeByDestinationStationSet = _stopStation_repository.findByStationId(destinationStationId).stream().map(stopstation -> stopstation.getRouteId()).toList();
        return routeByBoardingStationList.stream().filter(routeByDestinationStationSet::contains).distinct().toList();

    }

    public List<TrainEntity> getTrainByStation(String boardingStationId, String destinationStationId,Date departure_date) {
        List<String> routeSet = getRouteByStation(boardingStationId, destinationStationId);
        List<TrainEntity> trainList = routeSet.stream().map(routeId -> _train_repository.findByRouteId(routeId)).flatMap(Collection::stream).collect(Collectors.toList());
        trainList.forEach(train -> train.setSchedules(train.getSchedules().stream().filter(schedule -> schedule.getDeparture_date() == departure_date).toList()));
        return trainList;
    }
}
