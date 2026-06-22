package com.example.jreg0.Car;

import com.example.jreg0.car.CarController;
import com.example.jreg0.car.CarEntity;
import com.example.jreg0.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    CarEntity mockCar = new CarEntity();

    @BeforeEach
    void setup() {
        mockCar.setId("00000001");
    }

    @Test
    void getgetCarsByTrainId_NormalCase_列車Idと一致した列車の座席取得() throws  Exception {
        when(carService.getSeatsByTrainId("HYB001")).thenReturn(List.of(mockCar));

        mockMvc.perform(get("/cars/trains/HYB001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("00000001"));
        verify(carService).getSeatsByTrainId("HYB001");
    }
}
