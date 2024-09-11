package com.transdev.reservations.domain.model;

import java.time.LocalDate;

public record Reservation(Long id, LocalDate tripDate, String busNumber, Long clientId) {
}
