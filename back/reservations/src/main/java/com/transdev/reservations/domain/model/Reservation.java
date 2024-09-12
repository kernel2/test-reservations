package com.transdev.reservations.domain.model;

import java.time.LocalDateTime;

public record Reservation(Long id, LocalDateTime dateOfTravel, String busNumber, Long clientId) {
}
