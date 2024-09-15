package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.InvalidReservationException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.ReservationValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        Trip trip = new Trip(1L, "BUS123", LocalDateTime.now(), BigDecimal.TEN);
        Reservation reservation = new Reservation(1L, 1L, List.of(trip));

        // When & Then
        assertDoesNotThrow(() -> reservationValidatorService.validate(reservation));
    }

    @Test
    void testValidateWithNullDate() {
        // Given
        Trip trip = new Trip(1L, "BUS123", null, BigDecimal.TEN);
        Reservation reservation = new Reservation(1L, 1L, List.of(trip));

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Date of travel cannot be null.", exception.getMessage().trim());
    }

    @Test
    void testValidateWithNullBusNumber() {
        // Given
        Trip trip = new Trip(1L, null, LocalDateTime.now(), BigDecimal.TEN);
        Reservation reservation = new Reservation(1L, 1L, List.of(trip));

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Bus number cannot be null or empty.", exception.getMessage().trim());
    }

    @Test
    void testValidateWithEmptyBusNumber() {
        // Given
        Trip trip = new Trip(1L, "", LocalDateTime.now(), BigDecimal.TEN);
        Reservation reservation = new Reservation(1L, 1L, List.of(trip));

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Bus number cannot be null or empty.", exception.getMessage().trim());
    }

    @Test
    void testValidateWithNullClientId() {
        // Given
        Trip trip = new Trip(1L, "BUS123", LocalDateTime.now(), BigDecimal.TEN);
        Reservation reservation = new Reservation(1L, null, List.of(trip));

        // When & Then
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () ->
                reservationValidatorService.validate(reservation));
        assertEquals("Client ID cannot be null.", exception.getMessage().trim());
    }
}
