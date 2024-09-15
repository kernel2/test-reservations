package com.transdev.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Bus(String busNumber, int seatsPerTrip, LocalDateTime departureTime, BigDecimal pricePerTrip) {
}
