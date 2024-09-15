package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Buses")
public class BusEntity {

    @Id
    @Column(name = "bus_number")
    private String busNumber;
    @Column(name = "seats")
    private int seatsPerTrip;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "price")
    private BigDecimal pricePerTrip;

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getSeatsPerTrip() {
        return seatsPerTrip;
    }

    public void setSeatsPerTrip(int seatsPerTrip) {
        this.seatsPerTrip = seatsPerTrip;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public BigDecimal getPricePerTrip() {
        return pricePerTrip;
    }

    public void setPricePerTrip(BigDecimal pricePerTrip) {
        this.pricePerTrip = pricePerTrip;
    }
}