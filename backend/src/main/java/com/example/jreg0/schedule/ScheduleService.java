package com.example.jreg0.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private ScheduleRepository _scheduleRepository;

    @Autowired
    public void ScheduleRepository(ScheduleRepository scheduleRepository) {
        this._scheduleRepository = scheduleRepository;
    }

    public List<ScheduleEntity> getAll(){
        return _scheduleRepository.findAll();
    }
}

