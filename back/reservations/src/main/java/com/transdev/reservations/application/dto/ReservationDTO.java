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

    public Long getClientId() {
        return clientId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public LocalDateTime getDateOfTravel() {
        return dateOfTravel;
    }
}