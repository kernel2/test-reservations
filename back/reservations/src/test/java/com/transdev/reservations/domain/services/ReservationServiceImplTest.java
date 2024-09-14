package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.*;
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

    @InjectMocks
    private BillingServiceImpl billingService;

    @Mock
    private BillRepository billRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ReservationValidatorService reservationValidatorService;

    @Mock
    private DiscountService discountService;

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
        when(discountService.applyDiscountToReservation(any(Reservation.class), eq(busPrice)))
                .thenAnswer(invocation -> {
                    Reservation res = invocation.getArgument(0);
                    return new Reservation(res.id(), res.dateOfTravel(), res.busNumber(), res.clientId(), discountedPrice);
                });
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Check Reservation Fields
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Validate the reservation with discount applied
        verify(reservationRepository).save(argThat(res -> res.price().equals(discountedPrice)));
        assertNotNull(createdReservation);
        assertEquals(discountedPrice, createdReservation.price());
    }

    @Test
    void testCreateReservationWithoutDiscountAt100() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("100.00");

        // implement getBusPrice method in ReservationServiceImpl
        when(reservationRepository.getBusPrice("BUS123")).thenReturn(busPrice);
        when(discountService.applyDiscountToReservation(any(Reservation.class), eq(busPrice)))
                .thenAnswer(invocation -> {
                    Reservation res = invocation.getArgument(0);
                    return new Reservation(res.id(), res.dateOfTravel(), res.busNumber(), res.clientId(), busPrice);
                });
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Check Reservation Fields
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then: Verify no discount is applied
        verify(reservationRepository).save(argThat(res -> res.price().compareTo(busPrice) == 0));
        assertNotNull(createdReservation);
        assertEquals(busPrice, createdReservation.price());
    }

    @Test
    void testCreateReservationWithoutDiscountBelow100() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, BigDecimal.ZERO);
        BigDecimal busPrice = new BigDecimal("99.90");

        // implement getBusPrice method in ReservationServiceImpl
        when(reservationRepository.getBusPrice("BUS123")).thenReturn(busPrice);
        when(discountService.applyDiscountToReservation(any(Reservation.class), eq(busPrice)))
                .thenAnswer(invocation -> {
                    Reservation res = invocation.getArgument(0);
                    return new Reservation(res.id(), res.dateOfTravel(), res.busNumber(), res.clientId(), busPrice);
                });
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Check Reservation Fields
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then: Verify no discount is applied
        verify(reservationRepository).save(argThat(res -> res.price().compareTo(busPrice) == 0));
        assertNotNull(createdReservation);
        assertEquals(busPrice, createdReservation.price());
    }

    @Test
    void testCreateReservationWithNullPrice() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, null);
        BigDecimal busPrice = new BigDecimal("90.00");

        // implement getBusPrice method in ReservationServiceImpl
        when(reservationRepository.getBusPrice("BUS123")).thenReturn(busPrice);
        when(discountService.applyDiscountToReservation(any(Reservation.class), eq(busPrice)))
                .thenAnswer(invocation -> {
                    Reservation res = invocation.getArgument(0);
                    return new Reservation(res.id(), res.dateOfTravel(), res.busNumber(), res.clientId(), busPrice);
                });
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Check Reservation Fields
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then: Verify no discount is applied, and bus price is used
        verify(reservationRepository).save(argThat(res -> res.price().compareTo(busPrice) == 0));
        assertNotNull(createdReservation);
        assertEquals(busPrice, createdReservation.price());
    }

    @Test
    void testCreateReservationWithBusPriceException() {
        // Given
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", 1L, null);

        // Bus price is null or zero, which should raise an exception
        when(reservationRepository.getBusPrice("BUS123")).thenReturn(BigDecimal.ZERO);

        // Check Reservation Fields
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When & Then
        assertThrows(BusPriceException.class, () -> {
            reservationService.createReservation(reservation);
        });
    }


    @Test
    void testPayReservation() {
        Long reservationId = 1L;
        String paymentType = "Credit Card";
        when(paymentService.processPayment(reservationId, paymentType)).thenReturn(true);

        Bill billMock = new Bill(reservationId, paymentType);
        when(billRepository.save(any(Bill.class))).thenReturn(billMock);

        Bill bill = billingService.payReservation(reservationId, paymentType);
        verify(paymentService).processPayment(reservationId, paymentType);
        verify(billRepository).save(any(Bill.class));

        // Validate bill creation after successful payment
        assertNotNull(bill);
        assertEquals(reservationId, bill.reservationId());
        assertEquals(paymentType, bill.paymentType());
    }

    @Test
    void testFindReservationsByClientId() {
        Long clientId = 1L;
        Reservation reservation = new Reservation(1L, LocalDateTime.now(), "BUS123", clientId, BigDecimal.ZERO);
        when(reservationRepository.findByClientId(clientId)).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

        // Validate reservations are fetched correctly
        assertFalse(reservations.isEmpty());
        assertEquals(clientId, reservations.getFirst().clientId());
    }
}