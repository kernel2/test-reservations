package com.transdev.reservations.domain.exceptions;

public class ReservationAlreadyExistsException extends RuntimeException {

    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
