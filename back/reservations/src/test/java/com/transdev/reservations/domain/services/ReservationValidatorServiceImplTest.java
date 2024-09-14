package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.InvalidReservationException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.ReservationValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationValidatorServiceImplTest {

    private ReservationValidatorService reservationValidatorService;

    @BeforeEach
    void setUp() {
        reservationValidatorService = new ReservationValidatorServiceImpl();
    }

    @Test
    void testValidateWithValidReservation() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, BigDecimal.TEN);

        // When & Then
        assertDoesNotThrow(() -> reservationValidatorService.validate(reservation));
    }

    @Test
    void testValidateWithNullDate() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, BigDecimal.TEN);

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Date of travel cannot be null.", exception.getMessage());
    }

    @Test
    void testValidateWithNullBusNumber() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), null, 1L, BigDecimal.TEN);

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Bus number cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testValidateWithEmptyBusNumber() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "", 1L, BigDecimal.TEN);

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Bus number cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testValidateWithNullClientId() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", null, BigDecimal.TEN);

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Client ID cannot be null.", exception.getMessage());
    }
}
