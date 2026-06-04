package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.schedule.ScheduleRepository;
import com.example.jreg0.stopstation.StopStationEntity;
import com.example.jreg0.stopstation.StopStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private Set<String> getRouteByStation(String boardingStationId, String destinationStationId) {
        Set<String> routeByBoardingStationSet = (Set<String>) _stopStation_repository.findByStation(boardingStationId).stream().map(stopstation -> stopstation.getRoute_id()).toList();
        Set<String> routeByDestinationStationSet = (Set<String>) _stopStation_repository.findByStation(destinationStationId).stream().map(stopstation -> stopstation.getRoute_id()).toList();
        Set<String> routeIdByStationSet = new HashSet<>(routeByBoardingStationSet);
        routeIdByStationSet.retainAll(routeByDestinationStationSet);
        return routeIdByStationSet;
    }

    public List<TrainEntity> getTrainByStation(String boardingStationId, String destinationStationId,Date departure_date) {
        Set<String> routeSet = getRouteByStation(boardingStationId, destinationStationId);
        List<TrainEntity> trainList = routeSet.stream().map(routeId -> _train_repository.findByRoute(routeId)).flatMap(Collection::stream).collect(Collectors.toList());
        trainList.forEach(train -> train.setSchedules(train.getSchedules().stream().filter(schedule -> schedule.getDeparture_date() == departure_date).toList()));
        return trainList;
    }


}
