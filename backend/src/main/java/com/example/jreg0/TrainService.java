package com.example.jreg0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {
    private TrainRepository _repository;

    @Autowired
    public void TrainRepository(TrainRepository trainRepository) {
        this._repository = trainRepository;
    }

    public List<TrainEntity> getAll(){
        return _repository.findAll();
    }
}
