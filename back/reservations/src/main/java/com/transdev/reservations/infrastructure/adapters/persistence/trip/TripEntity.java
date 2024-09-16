package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import com.transdev.reservations.infrastructure.adapters.persistence.reservation.ReservationEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_seq")
    @SequenceGenerator(name = "trip_seq", sequenceName = "trips_seq", allocationSize = 1)
    private Long id;

    private String busNumber;

    private LocalDateTime dateOfTravel;

    private int seatsPerTrip;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

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

    public int getSeatsPerTrip() {
        return seatsPerTrip;
    }

    public void setSeatsPerTrip(int seatsPerTrip) {
        this.seatsPerTrip = seatsPerTrip;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
}
