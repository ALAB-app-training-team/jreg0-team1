package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
import com.example.jreg0.stopstation.StopStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainService {
    private final TrainRepository _trainRepository;
    private final StopStationRepository _stopStationRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository, StopStationRepository stopStationRepository) {
        this._trainRepository = trainRepository;
        this._stopStationRepository = stopStationRepository;
    }

    public List<TrainEntity> getAll() {
        return _trainRepository.findAll();
    }

    /**
     * 出発駅、到着駅、出発日が一致する列車の取得を行う
     *
     * @param departureStationId 出発駅
     * @param arrivalStationId 到着駅
     * @param departureDate 出発日
     * @return 出発駅、到着駅、出発日が一致する列車一覧
     * */
    public List<TrainEntity> getTrainByStation(String departureStationId, String arrivalStationId, LocalDate departureDate) {
        List<String> routeSet = getRouteIds(departureStationId, arrivalStationId);
        List<TrainEntity> trainList = routeSet.stream().map(_trainRepository::findByRouteId).flatMap(Collection::stream).collect(Collectors.toList());
        trainList.forEach(train -> train.setSchedules(train.getSchedules().stream().filter(schedule -> !schedule.getDepartureDate().isBefore(departureDate) && !schedule.getDepartureDate().isAfter(departureDate)).toList()));
        trainList = filterByDepartureStation(departureStationId,trainList);
        return sortByDepartureStation(departureStationId,trainList);
    }

    /**
     * 引数の2駅を含む経路を検索する
     *
     * @param StationIdA 1つ目の駅
     * @param StationIdB 2つ目の駅
     * @return 2つの駅を含む経路
     * */
    private List<String> getRouteIds(String StationIdA, String StationIdB) {
        List<String> routeByStationIdAList = _stopStationRepository.findByIdStationId(StationIdA).stream().map(stopstation -> stopstation.getId().getRouteId()).toList();
        List<String> routeByStationIdBList = _stopStationRepository.findByIdStationId(StationIdB).stream().map(stopstation -> stopstation.getId().getRouteId()).toList();
        return routeByStationIdAList.stream().filter(routeByStationIdBList::contains).distinct().toList();
    }

    /**
     * 出発駅が一致する列車の絞り込みを行う
     *
     * @param departureStationId 出発駅
     * @param trainList 列車一覧
     * @return 出発駅が一致する列車一覧
     * */
    private List<TrainEntity> filterByDepartureStation(String departureStationId, List<TrainEntity> trainList) {
        return trainList.stream().filter(train -> {
            ScheduleEntity earliestSchedule = train.getSchedules().stream().filter(schedule -> schedule.getDepartureTime() != null).min(Comparator.comparing(ScheduleEntity::getDepartureTime)).orElse(null);
            return earliestSchedule != null && departureStationId.equals(earliestSchedule.getStationId());
        }).toList();
    }

    /**
     * 出発時間が速い順でソートする
     *
     * @param departureStationId 出発駅
     * @param trainList 列車一覧
     * @return 出発駅が一致する列車一覧
     * */
    private List<TrainEntity> sortByDepartureStation(String departureStationId, List<TrainEntity> trainList) {
        return trainList.stream().sorted(Comparator.comparing(trainEntity -> trainEntity.getSchedules().stream().filter(scheduleEntity -> Objects.equals(scheduleEntity.getStationId(), departureStationId)).findFirst().map(ScheduleEntity::getDepartureTime).orElse(null),Comparator.nullsLast(Comparator.naturalOrder()))).toList();
    }
}
