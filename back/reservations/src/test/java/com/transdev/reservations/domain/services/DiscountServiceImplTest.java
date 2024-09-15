package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Mock
    private TripRepository tripRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyDiscountWhenPriceAboveThreshold() {
        // Given
        BigDecimal busPrice = new BigDecimal("150.00");
        BigDecimal expectedPrice = busPrice.subtract(busPrice.multiply(new BigDecimal("0.05")));
        expectedPrice = expectedPrice.setScale(2, RoundingMode.HALF_UP);

        // When
        BigDecimal discountedPrice = discountService.applyDiscountToPrice(busPrice);

        // Then
        assertEquals(expectedPrice, discountedPrice);

    }

    @Test
    void testApplyDiscountToPriceAtThreshold() {
        // Given
        BigDecimal price = new BigDecimal("100.00");

        // When
        BigDecimal discountedPrice = discountService.applyDiscountToPrice(price);

        // Then
        assertEquals(price, discountedPrice); // No discount applied at threshold
    }

    @Test
    void testApplyDiscountToPriceBelowThreshold() {
        // Given
        BigDecimal price = new BigDecimal("80.00");

        // When
        BigDecimal discountedPrice = discountService.applyDiscountToPrice(price);

        // Then
        assertEquals(price, discountedPrice); // No discount applied below threshold
    }

    @Test
    void testApplyDiscountsToTrips() {
        // Given
        Trip trip1 = new Trip(1L, "BUS123", LocalDateTime.now(), null);
        Trip trip2 = new Trip(2L, "BUS456", LocalDateTime.now(), null);

        // Mock bus prices
        when(tripRepository.getBusPrice("BUS123")).thenReturn(new BigDecimal("150.00"));
        when(tripRepository.getBusPrice("BUS456")).thenReturn(new BigDecimal("80.00"));

        // Expected discounted prices
        BigDecimal expectedPriceTrip1 = new BigDecimal("142.50"); // 5% discount
        BigDecimal expectedPriceTrip2 = new BigDecimal("80.00");  // No discount

        // When
        List<Trip> discountedTrips = discountService.applyDiscountsToTrips(Arrays.asList(trip1, trip2));

        // Then
        assertEquals(2, discountedTrips.size());

        Trip discountedTrip1 = discountedTrips.getFirst();
        assertEquals(expectedPriceTrip1, discountedTrip1.price());
        assertEquals(trip1.busNumber(), discountedTrip1.busNumber());
        assertEquals(trip1.dateOfTravel(), discountedTrip1.dateOfTravel());

        Trip discountedTrip2 = discountedTrips.get(1);
        assertEquals(expectedPriceTrip2, discountedTrip2.price());
        assertEquals(trip2.busNumber(), discountedTrip2.busNumber());
        assertEquals(trip2.dateOfTravel(), discountedTrip2.dateOfTravel());
    }

    @Test
    void testApplyDiscountToTripWithInvalidBusPrice() {
        // Given
        Trip trip = new Trip(1L, "BUS999", LocalDateTime.now(), null);

        // Mock bus price as null
        when(tripRepository.getBusPrice("BUS999")).thenReturn(null);

        // When & Then
        BusPriceException exception = assertThrows(BusPriceException.class, () -> {
            discountService.applyDiscountsToTrips(Arrays.asList(trip));
        });

        assertTrue(exception.getMessage().contains("Le prix du bus est introuvable ou invalide pour le numéro de bus : BUS999"));
    }
    @Test
    void testApplyDiscountToPriceWithRounding() {
        // Given
        BigDecimal price = new BigDecimal("99.50");
        BigDecimal expectedPrice = price.setScale(2, RoundingMode.HALF_UP);

        // When
        BigDecimal resultPrice = discountService.applyDiscountToPrice(price);

        // Then
        assertEquals(expectedPrice, resultPrice);
    }

}
