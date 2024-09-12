package com.transdev.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalTime;

public record Bus(String busNumber, int seatsPerTrip, LocalTime departureTime, BigDecimal pricePerTrip) {
}
