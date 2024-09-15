package com.transdev.reservations.domain.exceptions;

public final class ReservationAlreadyExistsException extends ApplicationException {
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
