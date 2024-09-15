package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.TripAlreadyExistsException;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createTripForBus_throwsException_whenTripAlreadyExists() {
        // Given
        String busNumber = "BUS001";
        LocalDateTime travelDateTime = LocalDateTime.of(2024, 9, 11, 8, 30);

        when(busRepository.existsTripOnDate(busNumber, travelDateTime)).thenReturn(true);

        // When & Then
        assertThrows(TripAlreadyExistsException.class, () -> busService.getTripsByBusAndDate(busNumber, travelDateTime));

        verify(busRepository, times(1)).existsTripOnDate(busNumber, travelDateTime);
    }

    @Test
    void createTripForBus_doesNotThrowException_whenNoTripExists() {
        // Given
        String busNumber = "BUS002";
        LocalDateTime travelDateTime = LocalDateTime.of(2024, 9, 12, 10, 30);

        when(busRepository.existsTripOnDate(busNumber, travelDateTime)).thenReturn(false);

        // When & Then
        busService.getTripsByBusAndDate(busNumber, travelDateTime);

        verify(busRepository, times(1)).existsTripOnDate(busNumber, travelDateTime);
    }
}