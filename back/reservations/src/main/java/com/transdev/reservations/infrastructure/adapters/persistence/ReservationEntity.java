package com.transdev.reservations.infrastructure.adapters.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String busNumber;
    private LocalDateTime dateOfTravel;


    public ReservationEntity(Long clientId, String busNumber, LocalDateTime dateOfTravel) {
        this.clientId = clientId;
        this.busNumber = busNumber;
        this.dateOfTravel = dateOfTravel;
    }

}
