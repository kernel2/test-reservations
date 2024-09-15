package com.transdev.reservations.domain.exceptions;

public final class PaymentFailedException extends ApplicationException{
    public PaymentFailedException(String message) {
        super(message);
    }
}