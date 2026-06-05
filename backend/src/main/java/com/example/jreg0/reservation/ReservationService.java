package com.example.jreg0.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReservationService {

    final private ReservationRepository _respository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        _respository = reservationRepository;
    }

    @Transactional
    public UUID resgistReservation(ReservationDTO reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setSeatId(reservation.getSeatId());
        entity.setReservationDate(reservation.getReservationDate());
        entity.setTrainId(reservation.getTrainId());
        entity.setBoardingStationId(reservation.getBoardingStationId());
        entity.setDestinationStationId(reservation.getDestinationStationId());
        entity.setPaymentMethod(reservation.getPaymentMethod());
        entity.setPaymentStatus(reservation.getPaymentStatus());
        entity.setFee(reservation.getFee());
        entity.setAccountId(reservation.getAccountId());
        _respository.save(entity);
        return entity.getId();
    }
}
