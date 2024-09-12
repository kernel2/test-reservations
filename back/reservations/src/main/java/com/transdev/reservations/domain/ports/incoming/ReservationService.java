package com.transdev.reservations.domain.ports.incoming;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation findReservationById(Long id);
    void cancelReservation(Long id);
    Bill payReservation(Long reservationId, String paymentType);
    void deleteReservation(Long id);
    List<Reservation> findReservationsByClientId(Long clientId);
}
