package com.transdev.reservations.application.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

public class BusDTO {


    private String busNumber;
    private int seatsPerTrip;
    private LocalTime departureTime;
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public BigDecimal getPricePerTrip() {
        return pricePerTrip;
    }

    public void setPricePerTrip(BigDecimal pricePerTrip) {
        this.pricePerTrip = pricePerTrip;
    }

    @Override
    public String toString() {
        return "BusDTO{" +
                "busNumber='" + busNumber + '\'' +
                ", seatsPerTrip=" + seatsPerTrip +
                ", departureTime=" + departureTime +
                ", pricePerTrip=" + pricePerTrip +
                '}';
    }
}