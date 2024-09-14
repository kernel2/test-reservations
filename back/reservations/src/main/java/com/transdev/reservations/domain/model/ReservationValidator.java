package com.transdev.reservations.domain.model;

import com.transdev.reservations.domain.exceptions.InvalidReservationException;

import java.math.BigDecimal;

public class ReservationValidator {

    private enum ValidationErrorType {
        DATE_NULL,
        BUS_NUMBER_NULL_OR_EMPTY,
        CLIENT_ID_NULL,
        VALID
    }

    public static void validate(Reservation reservation) {
        StringBuilder errorMessage = new StringBuilder();

        ValidationErrorType errorType = checkReservationFields(reservation);

        String error = switch (errorType) {
            case DATE_NULL -> "Date of travel cannot be null.";
            case BUS_NUMBER_NULL_OR_EMPTY -> "Bus number cannot be null or empty.";
            case CLIENT_ID_NULL -> "Client ID cannot be null.";
            default -> null;
        };

        if (error != null) {
            errorMessage.append(error).append(" ");
        }

        if (!errorMessage.isEmpty()) {
            throw new InvalidReservationException(errorMessage.toString().trim());
        }
    }

    private static ValidationErrorType checkReservationFields(Reservation reservation) {
        if (reservation.dateOfTravel() == null) {
            return ValidationErrorType.DATE_NULL;
        }
        if (reservation.busNumber() == null || reservation.busNumber().isEmpty()) {
            return ValidationErrorType.BUS_NUMBER_NULL_OR_EMPTY;
        }
        if (reservation.clientId() == null) {
            return ValidationErrorType.CLIENT_ID_NULL;
        }
        return ValidationErrorType.VALID;
    }


}
