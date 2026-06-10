package com.example.jreg0.train;

import com.example.jreg0.schedule.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TrainResponse {
    private TrainEntity train;
    private List<ScheduleEntity> scheduleEntities;
}
