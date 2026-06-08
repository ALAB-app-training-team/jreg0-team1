package com.example.jreg0.reservation;

import com.example.jreg0.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReservationService {

    private ReservationRepository _reservationRepository;
    private SeatRepository _seatRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, SeatRepository seatRepository) {
        _reservationRepository = reservationRepository;
        _seatRepository = seatRepository;
    }

    /**
     * 予約を登録するメソッド
     *
     * @param reservation id以外の予約情報の入った予約Entity
     * @return UUID 登録された予約の予約ID (UUID)
     */
    @Transactional
    public UUID resgisterReservation(ReservationEntity reservation) {
        // 今回は,座席を1Aに絞る
        reservation.setSeatId(_seatRepository.findAll().getFirst().getId());
        ReservationEntity saved = _reservationRepository.save(reservation);
        return saved.getId();
    }
}
