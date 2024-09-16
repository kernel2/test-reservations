package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BusServiceImplTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTripForBus_returnsExistingTrips_whenTripAlreadyExists() {
        // Given
        String busNumber = "BUS001";
        LocalDateTime travelDate = LocalDateTime.of(2024, 9, 11, 8, 30);
        LocalDateTime dateStart = travelDate.toLocalDate().atStartOfDay();
        LocalDateTime dateEnd = travelDate.toLocalDate().atTime(23, 59, 59);

        List<Trip> existingTrips = List.of(
                new Trip(1L, "BUS001", travelDate, 40,BigDecimal.valueOf(50.00))
        );

        when(busRepository.findTripsByBusAndDate(busNumber, dateStart, dateEnd)).thenReturn(existingTrips);

        // When
        List<Trip> trips = busService.getTripsByBusAndDate(busNumber, dateStart, dateEnd);

        // Then
        assertEquals(existingTrips, trips);
        verify(busRepository, times(1)).findTripsByBusAndDate(busNumber, dateStart, dateEnd);
    }

    @Test
    void createTripForBus_doesNotThrowException_whenNoTripExists() {
        // Given
        String busNumber = "BUS002";
        LocalDateTime travelDate = LocalDateTime.of(2024, 9, 12, 10, 30);
        LocalDateTime dateStart = travelDate.toLocalDate().atStartOfDay();
        LocalDateTime dateEnd = travelDate.toLocalDate().atTime(23, 59, 59);

        when(busRepository.findTripsByBusAndDate(busNumber, dateStart, dateEnd)).thenReturn(Collections.emptyList());

        // When
        busService.getTripsByBusAndDate(busNumber, dateStart, dateEnd);

        // Then
        verify(busRepository, times(1)).findTripsByBusAndDate(busNumber, dateStart, dateEnd);
    }
}