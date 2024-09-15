package com.transdev.reservations.domain.exceptions;

public final class PaymentAlreadyProcessedException extends ApplicationException {
    public PaymentAlreadyProcessedException(String message) {
        super(message);
    }
}