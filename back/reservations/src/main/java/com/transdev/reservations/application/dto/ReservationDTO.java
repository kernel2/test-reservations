package com.transdev.reservations.application.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long id;
    private Long clientId;
    private String busNumber;
    private LocalDateTime dateOfTravel;


    public ReservationDTO(Long id, Long clientId, String busNumber, LocalDateTime dateOfTravel) {
        this.id = id;
        this.clientId = clientId;
        this.busNumber = busNumber;
        this.dateOfTravel = dateOfTravel;
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

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public LocalDateTime getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(LocalDateTime dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", busNumber='" + busNumber + '\'' +
                ", dateOfTravel=" + dateOfTravel +
                '}';
    }
}