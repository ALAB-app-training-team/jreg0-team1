package com.example.jreg0.train;
import com.example.jreg0.schedule.ScheduleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "trains")
public class TrainController {
    private final TrainService _trainService;

    @Autowired
    public TrainController(TrainService trainService){
        this._trainService = trainService;
    }

    @GetMapping
    public List<TrainResponseDto> getTrains(
            @RequestParam(name="start") String departureStationId,
            @RequestParam(name="end") String arrivalStationId,
            @RequestParam(name="date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate){
        List<TrainEntity> trainEntities = this._trainService.getTrainByStation(departureStationId,arrivalStationId,departureDate);
        return trainEntities.stream().map(this::convertToTrainResponseDto).collect(Collectors.toList());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidDeoar(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * 列車Entity→DTOの変換を行う
     *
     * @param train Entityの列車
     * @return DTOの列車
     * */
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
