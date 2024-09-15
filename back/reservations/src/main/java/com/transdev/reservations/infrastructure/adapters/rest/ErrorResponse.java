package com.transdev.reservations.infrastructure.adapters.rest;

public record ErrorResponse(int status, String error, String message, String path) {
}
