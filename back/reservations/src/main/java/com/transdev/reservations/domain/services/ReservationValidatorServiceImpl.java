package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.InvalidReservationException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.ReservationValidatorService;

public class ReservationValidatorServiceImpl implements ReservationValidatorService {

    private enum ValidationErrorType {
        DATE_NULL,
        BUS_NUMBER_NULL_OR_EMPTY,
        VALID
    }

    @Override
    public void validate(Reservation reservation) {
        StringBuilder errorMessage = new StringBuilder();

        if (reservation.clientId() == null) {
            errorMessage.append("Client ID cannot be null. ");
        }

        if (reservation.trips() == null || reservation.trips().isEmpty()) {
            errorMessage.append("Reservation must contain at least one trip. ");
        } else {
            for (Trip trip : reservation.trips()) {
                ValidationErrorType errorType = checkTripFields(trip);

                String error = switch (errorType) {
                    case DATE_NULL -> "Date of travel cannot be null.";
                    case BUS_NUMBER_NULL_OR_EMPTY -> "Bus number cannot be null or empty.";
                    default -> null;
                };

                if (error != null) {
                    errorMessage.append(error).append(" ");
                }
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new InvalidReservationException(errorMessage.toString().trim());
        }
    }

    private ValidationErrorType checkTripFields(Trip trip) {
        if (trip.dateOfTravel() == null) {
            return ValidationErrorType.DATE_NULL;
        }
        if (trip.busNumber() == null || trip.busNumber().isEmpty()) {
            return ValidationErrorType.BUS_NUMBER_NULL_OR_EMPTY;
        }
        return ValidationErrorType.VALID;
    }
}
