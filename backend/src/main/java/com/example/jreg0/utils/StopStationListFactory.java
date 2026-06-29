package com.example.jreg0.utils;

import com.example.jreg0.schedule.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Component
public class StopStationListFactory {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public StopStationListFactory(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public StopStationList create(String trainId, LocalDate departureDate) {
        return new StopStationList(setStationList(trainId, departureDate));
    }

    /**
     * 列車名と日付から、該当の列車が停車する駅の駅IDを持つ双方向リストを作成するpriavateメソッド
     *
     * @param trainId       列車Id(例: HYB001)
     * @param departureDate 出発日
     * @return 列車が停車する各駅の駅IDを持つLinkedList
     */
    private LinkedList<String> setStationList(String trainId, LocalDate departureDate) {
        return scheduleRepository.findByTrainIdAndDepartureDate(trainId, departureDate).stream()
                .sorted(Comparator.comparing(
                        ScheduleEntity::getDepartureTime,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .map(schedule -> schedule.getStationId())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
