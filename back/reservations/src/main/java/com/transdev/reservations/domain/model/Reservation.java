package com.transdev.reservations.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Reservation(UUID id, LocalDate tripDate, String busNumber, UUID clientId) {
}
