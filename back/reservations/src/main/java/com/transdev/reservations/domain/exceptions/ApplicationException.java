package com.transdev.reservations.domain.exceptions;

public sealed class ApplicationException extends RuntimeException
        permits BusPriceException, InvalidReservationException, PaymentAlreadyProcessedException, PaymentFailedException, ReservationAlreadyExistsException, ResourceAlreadyExistsException, ResourceNotFoundException, UnexpectedErrorException {

    public ApplicationException(String message) {
        super(message);
    }
}