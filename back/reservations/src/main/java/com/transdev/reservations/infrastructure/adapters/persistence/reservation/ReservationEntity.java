package com.transdev.reservations.infrastructure.adapters.persistence.reservation;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String busNumber;
    private LocalDateTime dateOfTravel;


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
