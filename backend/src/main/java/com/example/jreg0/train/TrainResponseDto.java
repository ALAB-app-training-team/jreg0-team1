package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainResponseDto {
    private String Id;
    private String trainNumber;
    private String trainName;
    private String routeId;
    private String trainNickname;
    private Integer formation;
    private List<ScheduleResponseDto> schedules;
}
