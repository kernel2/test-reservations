package com.transdev.reservations.domain.exceptions;

public final class InvalidReservationException extends ApplicationException {
    public InvalidReservationException(String message) {
        super(message);
    }
}