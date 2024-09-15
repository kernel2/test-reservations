package com.transdev.reservations.domain.exceptions;

public final class TripAlreadyExistsException extends ApplicationException {
    public TripAlreadyExistsException(String message) {
        super(message);
    }
}
