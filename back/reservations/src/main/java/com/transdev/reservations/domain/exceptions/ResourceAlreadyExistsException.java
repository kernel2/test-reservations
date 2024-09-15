package com.transdev.reservations.domain.exceptions;

public final class ResourceAlreadyExistsException extends ApplicationException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
