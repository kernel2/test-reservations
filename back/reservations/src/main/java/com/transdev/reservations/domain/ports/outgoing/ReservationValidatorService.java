package com.transdev.reservations.domain.ports.outgoing;


import com.transdev.reservations.domain.model.Reservation;

public interface ReservationValidatorService {
     void validate(Reservation reservation);
}