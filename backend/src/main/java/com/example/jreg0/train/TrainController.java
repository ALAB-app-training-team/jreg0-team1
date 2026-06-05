package com.example.jreg0.train;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/trains")
public class TrainController {
    private TrainService trainService;
    @GetMapping
    public List<TrainResponseDTO> getTrains(
            @RequestParam String boardingStationId,
            @RequestParam String destinationStationId,
            @RequestParam Date departure_date){
        return this.trainService.getTrainByStation(boardingStationId,destinationStationId,departure_date);
    }
}