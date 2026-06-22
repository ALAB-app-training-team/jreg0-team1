package com.example.jreg0.car;

import com.example.jreg0.seat.SeatEntity;
import com.example.jreg0.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository _carRepository;
    @Autowired
    public CarService(
            CarRepository carRepository
    ){
        _carRepository = carRepository;
    };

    public List<CarEntity> getSeatsByTrainId(String trainId){
        List<CarEntity> carEntities = _carRepository.findByTrainId(trainId);
        return carEntities;
    }
}
