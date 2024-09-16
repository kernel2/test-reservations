package com.transdev.reservations.domain.ports.incoming;

import com.transdev.reservations.domain.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);

    Reservation findReservationById(Long id);

    void cancelReservation(Long id);

    void deleteReservation(Long id);

    List<Reservation> findReservationsByClientId(Long clientId);

    Reservation updateReservation(Long reservationId, Reservation reservation);
}
