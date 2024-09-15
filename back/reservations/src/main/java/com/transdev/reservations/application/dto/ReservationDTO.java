package com.transdev.reservations.application.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReservationDTO {
    private Long id;
    private Long clientId;
    private List<TripDTO> trips;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long clientId, List<TripDTO> trips) {
        this.id = id;
        this.clientId = clientId;
        this.trips = trips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<TripDTO> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDTO> trips) {
        this.trips = trips;
    }

    public BigDecimal getTotalPrice() {
        return trips.stream()
                .map(TripDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", trips=" + trips +
                '}';
    }
}