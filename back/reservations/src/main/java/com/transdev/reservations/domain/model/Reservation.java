package com.transdev.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Reservation(Long id, LocalDateTime dateOfTravel, String busNumber, Long clientId, BigDecimal price) {

    public Reservation withPrice(BigDecimal newPrice) {
        return new Reservation(this.id, this.dateOfTravel, this.busNumber, this.clientId, newPrice);
    }
}
