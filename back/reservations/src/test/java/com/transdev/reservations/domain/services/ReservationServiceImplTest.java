package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservationWithDiscount() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("150.00");
        BigDecimal discountedPrice = busPrice.subtract(busPrice.multiply(new BigDecimal("0.05")));

        // implement getBusPrice methode in ReservationServiceImpl
        when(reservationRepository.getBusPrice("BUS123")).thenReturn(busPrice);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation res = invocation.getArgument(0);
            return new Reservation(res.id(), res.dateOfTravel(), res.busNumber(), res.clientId(), discountedPrice);
        });

        Reservation createdReservation = reservationService.createReservation(reservation);

        // Validate the reservation with discount applied
        verify(reservationRepository).save(argThat(res -> res.price().equals(discountedPrice)));
        assertNotNull(createdReservation);
        assertEquals(discountedPrice, createdReservation.price());

    }

    @Test
    void testPayReservation() {
        Long reservationId = 1L;
        String paymentType = "Credit Card";
        when(paymentService.processPayment(reservationId, paymentType)).thenReturn(true);

        Bill bill = reservationService.payReservation(reservationId, paymentType);

        // Validate bill creation after successful payment
        assertEquals(reservationId, bill.reservationId());
        assertEquals(paymentType, bill.paymentType());
    }

    @Test
    void testFindReservationsByClientId() {
        Long clientId = 1L;
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", clientId,BigDecimal.ZERO);
        when(reservationRepository.findByClientId(clientId)).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

        // Validate reservations are fetched correctly
        assertFalse(reservations.isEmpty());
        assertEquals(clientId, reservations.getFirst().clientId());
    }
}