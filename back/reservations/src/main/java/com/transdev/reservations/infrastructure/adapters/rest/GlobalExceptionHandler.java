package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, HttpServletRequest request) {
        int status = getStatus(ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status,
                HttpStatus.valueOf(status).getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    private int getStatus(ApplicationException ex) {
        if (ex instanceof InvalidReservationException || ex instanceof BusPriceException || ex instanceof PaymentFailedException) {
            return HttpStatus.BAD_REQUEST.value();
        } else if (ex instanceof ReservationAlreadyExistsException || ex instanceof PaymentAlreadyProcessedException) {
            return HttpStatus.CONFLICT.value();
        } else if (ex instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND.value();
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }
}
