package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    void delete(Reservation reservation);

    void deleteById(Long id);

    List<Reservation> findByClientId(Long clientId);

}