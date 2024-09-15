package com.transdev.reservations.domain.exceptions;

public sealed class ApplicationException extends RuntimeException
        permits BusPriceException, InvalidReservationException, PaymentAlreadyProcessedException, PaymentFailedException, ReservationAlreadyExistsException, ResourceAlreadyExistsException, ResourceNotFoundException, TripAlreadyExistsException, UnexpectedErrorException {

    public ApplicationException(String message) {
        super(message);
    }
}