package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "Buses")
public class BusEntity {

    @Id
    @Column(name = "bus_number")
    private String busNumber;
    @Column(name = "seats")
    private int seatsPerTrip;
    @Column(name = "departure_time")
    private LocalTime departureTime;
    @Column(name = "price")
    private BigDecimal pricePerTrip;

    public String getBusNumber() {
        return busNumber;
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