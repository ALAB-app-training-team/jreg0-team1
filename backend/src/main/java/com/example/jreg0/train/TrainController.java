package com.example.jreg0.train;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "trains")
public class TrainController {
    private final TrainService _trainService;

    public TrainController(TrainService trainService){
        this._trainService = trainService;
    }

    @GetMapping
    public List<TrainResponseDto> getTrains(
            @RequestParam(name="start") String boardingStationId,
            @RequestParam(name="end") String destinationStationId,
            @RequestParam(name="date") Date departure_date){
        List<TrainEntity> trainEntities = this._trainService.getTrainByStation(boardingStationId,destinationStationId,departure_date);

        return trainEntities.stream().map(this::convertToTrainResponseDto).collect(Collectors.toList());
    }

    /**
     * 列車Entity→DTOの変換を行う
     *
     * @param train Entityの列車
     * @return DTOの列車
     * */
    private TrainResponseDto convertToTrainResponseDto(TrainEntity train) {
        TrainResponseDto dto = new TrainResponseDto();
        dto.setId(train.getId());
        dto.setTrainNumber(train.getTrainNumber());
        dto.setTrainName(train.getTrainName());
        dto.setRouteId(train.getRouteId());
        dto.setTrainNickname(train.getTrainNickname());
        dto.setFormation(train.getFormation());
        dto.setSchedules(train.getSchedules());
        return dto;
    }
}
