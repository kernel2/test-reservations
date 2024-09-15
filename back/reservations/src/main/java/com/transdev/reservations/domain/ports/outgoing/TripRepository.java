package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Trip;

import java.math.BigDecimal;
import java.util.Optional;

public interface TripRepository {
    Trip save(Trip Trip);

    Optional<Trip> findById(Long id);

    void deleteById(Long id);

    BigDecimal getBusPrice(String busNumber);

}