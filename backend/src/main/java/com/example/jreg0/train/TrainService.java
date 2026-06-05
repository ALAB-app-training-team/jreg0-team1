package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
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

    /**
     * 引数の2駅を含む経路を検索する
     *
     * @param StationIdA 1つ目の駅
     * @param StationIdB 2つ目の駅
     * @return 2つの駅を含む経路
     * */
    private List<String> getRouteByStation(String StationIdA, String StationIdB) {
        List<String> routeByStationIdAList = _stopStation_repository.findByStationId(StationIdA).stream().map(stopstation -> stopstation.getRouteId()).toList();
        List<String> routeByStationIdBList = _stopStation_repository.findByStationId(StationIdB).stream().map(stopstation -> stopstation.getRouteId()).toList();
        return routeByStationIdAList.stream().filter(routeByStationIdBList::contains).distinct().toList();
    }

    /**
     * 出発駅が一致する列車の絞り込みを行う
     *
     * @param boardingStationId 出発駅
     * @param trainList 列車一覧
     * @return 出発駅が一致する列車一覧
     * */
    private List<TrainEntity> filterByDepartureStation(String boardingStationId, List<TrainEntity> trainList) {
        return trainList.stream().filter(train -> {
            ScheduleEntity earliestSchedule = train.getSchedules().stream().filter(schedule -> schedule.getDepartureTime() != null).min(Comparator.comparing(ScheduleEntity::getDepartureTime)).orElse(null);
            return earliestSchedule != null && boardingStationId.equals(earliestSchedule.getStationId());
        }).toList();
    }

    /**
     * 出発駅、到着駅、出発日が一致する列車の取得を行う
     *
     * @param boardingStationId 出発駅
     * @param destinationStationId 到着駅
     * @param departure_date 出発日
     * @return 出発駅、到着駅、出発日が一致する列車一覧
     * */
    public List<TrainEntity> getTrainByStation(String boardingStationId, String destinationStationId, Date departure_date) {
        List<String> routeSet = getRouteByStation(boardingStationId, destinationStationId);
        List<TrainEntity> trainList = routeSet.stream().map(routeId -> _train_repository.findByRouteId(routeId)).flatMap(Collection::stream).collect(Collectors.toList());
        trainList.forEach(train -> train.setSchedules(train.getSchedules().stream().filter(schedule -> schedule.getDepartureDate() != departure_date).toList()));
        trainList = filterByDepartureStation(boardingStationId,trainList);
        return trainList;
    }
}

