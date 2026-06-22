package com.example.jreg0.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "cars")
public class CarController {
    private final CarService _carService;

    @Autowired
    public  CarController(CarService carService){
        _carService = carService;
    }

    @GetMapping(value = "trains/{id}")
    public ResponseEntity<List<CarEntity>> getCarsByTrainId(@PathVariable("id") String trainId){
        List<CarEntity> carEntities = _carService.getSeatsByTrainId(trainId);

        return ResponseEntity.ok(carEntities);
    }
}
