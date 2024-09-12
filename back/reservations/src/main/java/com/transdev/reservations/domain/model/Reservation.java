package com.transdev.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Reservation(Long id, LocalDateTime dateOfTravel, String busNumber, Long clientId, BigDecimal price) {

    public Reservation {
        if (dateOfTravel == null || busNumber == null || clientId == null || price == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
    }

    public Reservation withPrice(BigDecimal newPrice) {
        return new Reservation(this.id, this.dateOfTravel, this.busNumber, this.clientId, newPrice);
    }
}
