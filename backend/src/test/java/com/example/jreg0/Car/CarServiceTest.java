package com.example.jreg0.Car;

import com.example.jreg0.car.CarEntity;
import com.example.jreg0.car.CarRepository;
import com.example.jreg0.car.CarService;
import com.example.jreg0.seat.SeatEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    CarEntity mockCar = new CarEntity();

    SeatEntity mockSeat1 = new SeatEntity();
    SeatEntity mockSeat2 = new SeatEntity();

    @BeforeEach
    void setup() {
        mockSeat1.setId("00000001");
        mockSeat2.setId("00000002");
        mockCar.setId("00000001");
        mockCar.setTrainId("HYB001");
        mockCar.setSeats(List.of(mockSeat1,mockSeat2));
    }

    @Test
    void getSeatsByTrainId_NormalCase_列車Idが一致した座席取得(){

        List<CarEntity> expected = List.of(mockCar);

        when(carRepository.findByTrainId("HYB001")).thenReturn(expected);

        List<CarEntity> carEntities = carService.getSeatsByTrainId("HYB001");

        assertEquals(expected, carEntities);
        verify(carRepository).findByTrainId("HYB001");
    }
}
