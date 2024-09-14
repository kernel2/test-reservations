package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceImplTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    void testApplyDiscountWhenPriceAboveThreshold() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("150.00");
        BigDecimal expectedDiscountedPrice = busPrice.subtract(busPrice.multiply(new BigDecimal("0.05")));

        // When
        Reservation result = discountService.applyDiscountToReservation(reservation, busPrice);

        // Then
        assertEquals(expectedDiscountedPrice, result.price());
        assertEquals(reservation.id(), result.id());
        assertEquals(reservation.dateOfTravel(), result.dateOfTravel());
        assertEquals(reservation.busNumber(), result.busNumber());
        assertEquals(reservation.clientId(), result.clientId());
    }

    @Test
    void testApplyDiscountWhenPriceExactlyAtThreshold() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("100.00");

        // When
        Reservation result = discountService.applyDiscountToReservation(reservation, busPrice);

        // Then
        assertEquals(busPrice, result.price());  // No discount applied because the price is exactly at the threshold
        assertEquals(reservation.id(), result.id());
        assertEquals(reservation.dateOfTravel(), result.dateOfTravel());
        assertEquals(reservation.busNumber(), result.busNumber());
        assertEquals(reservation.clientId(), result.clientId());
    }

    @Test
    void testApplyDiscountWhenPriceBelowThreshold() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("80.00");

        // When
        Reservation result = discountService.applyDiscountToReservation(reservation, busPrice);

        // Then
        assertEquals(busPrice, result.price());  // No discount applied because the price is below the threshold
        assertEquals(reservation.id(), result.id());
        assertEquals(reservation.dateOfTravel(), result.dateOfTravel());
        assertEquals(reservation.busNumber(), result.busNumber());
        assertEquals(reservation.clientId(), result.clientId());
    }

    @Test
    void testApplyDiscountWhenReservationPriceIsNull() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, null);
        BigDecimal busPrice = new BigDecimal("90.00");

        // When
        Reservation result = discountService.applyDiscountToReservation(reservation, busPrice);

        // Then
        assertEquals(busPrice, result.price());  // No discount applied because reservation price is null
        assertEquals(reservation.id(), result.id());
        assertEquals(reservation.dateOfTravel(), result.dateOfTravel());
        assertEquals(reservation.busNumber(), result.busNumber());
        assertEquals(reservation.clientId(), result.clientId());
    }

    @Test
    void testApplyDiscountWhenReservationPriceIsNonZero() {
        // Given
        Reservation reservation = new Reservation(1L, null, "BUS123", 1L, new BigDecimal("50.00"));
        BigDecimal busPrice = new BigDecimal("120.00");
        BigDecimal expectedDiscountedPrice = busPrice.subtract(busPrice.multiply(new BigDecimal("0.05")));

        // When
        Reservation result = discountService.applyDiscountToReservation(reservation, busPrice);

        // Then
        assertEquals(expectedDiscountedPrice, result.price());  // Discount should be applied
        assertEquals(reservation.id(), result.id());
        assertEquals(reservation.dateOfTravel(), result.dateOfTravel());
        assertEquals(reservation.busNumber(), result.busNumber());
        assertEquals(reservation.clientId(), result.clientId());
    }
}
