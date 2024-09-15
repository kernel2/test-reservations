package com.transdev.reservations.domain.model;

import java.util.List;

public record Reservation(Long id, Long clientId, List<Trip> trips) {
}
