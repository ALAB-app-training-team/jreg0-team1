package com.example.jreg0.Reservation;

import com.example.jreg0.reservation.ReservationController;
import com.example.jreg0.reservation.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class ReservationControllerTest {

    @Mock
    private ReservationService _serviceMock;

    @InjectMocks
    private ReservationController _controller;

    @Test
    public void test() {
        Assertions.assertEquals(1+1,2);
    }

    @Nested
    public class ReserveSeatTest {
        @Test
        void testReserveation_success() {

        }
    }

}
