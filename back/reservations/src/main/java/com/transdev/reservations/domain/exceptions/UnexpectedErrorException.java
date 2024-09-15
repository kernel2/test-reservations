package com.transdev.reservations.domain.exceptions;

public final class UnexpectedErrorException extends ApplicationException {
    public UnexpectedErrorException(String message) {
        super(message);
    }
}