package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "Buses")
public class BusEntity {

    @Id
    private String number;
    private int seatsPerTrip;
    private LocalTime departureTime;
    private BigDecimal pricePerTrip;

    public String getNumber() {
        return number;
    }

    public int getSeatsPerTrip() {
        return seatsPerTrip;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public BigDecimal getPricePerTrip() {
        return pricePerTrip;
    }
}