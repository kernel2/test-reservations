package com.transdev.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Trip(Long id, String busNumber, LocalDateTime dateOfTravel, int seatsPerTrip, BigDecimal price) {
}
