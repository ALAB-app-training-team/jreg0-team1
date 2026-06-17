package com.example.jreg0.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository _scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this._scheduleRepository = scheduleRepository;
    }

    public List<ScheduleEntity> getAll(){
        return _scheduleRepository.findAll();
    }
}

