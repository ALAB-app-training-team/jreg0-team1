package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "trains")
public class TrainController {
    private final TrainService _trainService;

    public TrainController(TrainService trainService) {
        this._trainService = trainService;
    }

    @GetMapping
    public List<TrainResponseDto> getTrains(
            @RequestParam(name = "start") String departureStationId,
            @RequestParam(name = "end") String arrivalStationId,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate) {
        List<TrainEntity> trainEntities = this._trainService.getTrainByStation(departureStationId, arrivalStationId, departureDate);

        return trainEntities.stream().map(this::convertToTrainResponseDto).collect(Collectors.toList());
    }

    /**
     * 列車Entity→DTOの変換を行う
     *
     * @param train Entityの列車
     * @return DTOの列車
     *
     */
    private TrainResponseDto convertToTrainResponseDto(TrainEntity train) {

        List<ScheduleResponseDto> scheduleResponseDtos = new ArrayList<>(train.getSchedules().stream().map(s ->
                new ScheduleResponseDto(
                        s.getId(),
                        s.getStationId(),
                        s.getDepartureTime(),
                        s.getArrivalTime(),
                        s.getDepartureTrack(),
                        s.getDepartureDate()
                )).toList());

        TrainResponseDto dto = new TrainResponseDto(
                train.getId(),
                train.getTrainNumber(),
                train.getTrainName(),
                train.getRouteId(),
                train.getTrainNickname(),
                train.getFormation(),
                scheduleResponseDtos
        );
        return dto;
    }
}
