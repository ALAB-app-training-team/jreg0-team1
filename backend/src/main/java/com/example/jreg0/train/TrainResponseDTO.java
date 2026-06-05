package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
import lombok.Data;

import java.util.List;
@Data
public class TrainResponseDTO {
    private String Id;
    private String trainNumber;
    private String trainName;
    private String routeId;
    private String trainNickname;
    private Integer formation;
    private List<ScheduleEntity> schedules;
}
