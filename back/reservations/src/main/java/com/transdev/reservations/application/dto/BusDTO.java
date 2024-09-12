package com.transdev.reservations.application.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public class BusDTO {


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