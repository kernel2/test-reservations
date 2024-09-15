package com.transdev.reservations.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripDTO {
    private Long id;
    private String busNumber;
    private LocalDateTime dateOfTravel;
    private BigDecimal price;

    public TripDTO() {
    }

    public TripDTO(Long id, String busNumber, LocalDateTime dateOfTravel, BigDecimal price) {
        this.id = id;
        this.busNumber = busNumber;
        this.dateOfTravel = dateOfTravel;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", busNumber='" + busNumber + '\'' +
                ", dateOfTravel=" + dateOfTravel +
                ", price=" + price +
                '}';
    }
}
