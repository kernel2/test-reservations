package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Reservation;

import java.math.BigDecimal;

public interface DiscountService {
    Reservation applyDiscountToReservation(Reservation reservation, BigDecimal busPrice);
}