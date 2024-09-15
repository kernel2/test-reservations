package com.transdev.reservations.domain.exceptions;

public sealed class ApplicationException extends RuntimeException
        permits InvalidReservationException, ReservationAlreadyExistsException,
        BusPriceException, ResourceNotFoundException, PaymentFailedException,
        PaymentAlreadyProcessedException, UnexpectedErrorException {

    public ApplicationException(String message) {
        super(message);
    }
}