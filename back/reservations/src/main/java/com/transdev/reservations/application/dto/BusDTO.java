package com.transdev.reservations.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BusDTO {


    private String busNumber;
    private int seatsPerTrip;
    private LocalDateTime departureTime;
    private BigDecimal pricePerTrip;

    public BusDTO() {
    }

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