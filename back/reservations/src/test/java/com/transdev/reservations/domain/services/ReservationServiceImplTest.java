package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.outgoing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
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
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), null);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(null, 1L, trips);

        BigDecimal busPrice = new BigDecimal("150.00");
        BigDecimal discountedPrice = busPrice.subtract(busPrice.multiply(new BigDecimal("0.05")));
        discountedPrice = discountedPrice.setScale(2, RoundingMode.HALF_UP);

        // Mock discountService to return trips with discounted prices
        Trip discountedTrip = new Trip(null, "BUS123", trip.dateOfTravel(), discountedPrice);
        List<Trip> discountedTrips = List.of(discountedTrip);

        when(discountService.applyDiscountsToTrips(anyList())).thenReturn(discountedTrips);

        // Mock reservationRepository.save to return a saved reservation
        Reservation savedReservation = new Reservation(1L, 1L, discountedTrips);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Mock reservationValidatorService.validate to do nothing
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // Mock reservationRepository.findByClientId to return empty list (no existing reservations)
        when(reservationRepository.findByClientId(1L)).thenReturn(Collections.emptyList());

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then
        verify(reservationValidatorService).validate(any(Reservation.class));
        verify(discountService).applyDiscountsToTrips(anyList());
        verify(reservationRepository).save(any(Reservation.class));

        assertNotNull(createdReservation);
        assertEquals(1L, createdReservation.id());
        assertEquals(1L, createdReservation.clientId());
        assertNotNull(createdReservation.trips());
        assertEquals(1, createdReservation.trips().size());
        Trip createdTrip = createdReservation.trips().getFirst();
        assertEquals("BUS123", createdTrip.busNumber());
        assertEquals(discountedPrice, createdTrip.price());
    }

    @Test
    void testCreateReservationWithoutDiscountAt100() {
        // Given
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), null);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(null, 1L, trips);

        BigDecimal busPrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = busPrice.setScale(2, RoundingMode.HALF_UP);

        // Mock discountService to return trips without discount
        Trip tripWithPrice = new Trip(null, "BUS123", trip.dateOfTravel(), expectedPrice);
        List<Trip> tripsWithPrice = List.of(tripWithPrice);

        when(discountService.applyDiscountsToTrips(anyList())).thenReturn(tripsWithPrice);

        // Mock reservationRepository.save
        Reservation savedReservation = new Reservation(1L, 1L, tripsWithPrice);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Mock reservationValidatorService.validate
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // Mock reservationRepository.findByClientId
        when(reservationRepository.findByClientId(1L)).thenReturn(Collections.emptyList());

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then
        verify(reservationValidatorService).validate(any(Reservation.class));
        verify(discountService).applyDiscountsToTrips(anyList());
        verify(reservationRepository).save(any(Reservation.class));

        assertNotNull(createdReservation);
        assertEquals(expectedPrice, createdReservation.trips().getFirst().price());
    }

    @Test
    void testCreateReservationWithoutDiscountBelow100() {
        // Given
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), null);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(null, 1L, trips);

        BigDecimal busPrice = new BigDecimal("99.90");
        BigDecimal expectedPrice = busPrice.setScale(2, RoundingMode.HALF_UP);

        // Mock discountService to return trips without discount
        Trip tripWithPrice = new Trip(null, "BUS123", trip.dateOfTravel(), expectedPrice);
        List<Trip> tripsWithPrice = List.of(tripWithPrice);

        when(discountService.applyDiscountsToTrips(anyList())).thenReturn(tripsWithPrice);

        // Mock reservationRepository.save
        Reservation savedReservation = new Reservation(1L, 1L, tripsWithPrice);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Mock reservationValidatorService.validate
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // Mock reservationRepository.findByClientId
        when(reservationRepository.findByClientId(1L)).thenReturn(Collections.emptyList());

        // When
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Then
        verify(reservationValidatorService).validate(any(Reservation.class));
        verify(discountService).applyDiscountsToTrips(anyList());
        verify(reservationRepository).save(any(Reservation.class));

        assertNotNull(createdReservation);
        assertEquals(expectedPrice, createdReservation.trips().getFirst().price());
    }

    @Test
    void testCreateReservationWithBusPriceException() {
        // Given
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), null);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(null, 1L, trips);

        // Mock discountService to throw BusPriceException
        when(discountService.applyDiscountsToTrips(anyList()))
                .thenThrow(new BusPriceException("Le prix du bus est introuvable ou invalide pour le numéro de bus : BUS123"));

        // Mock reservationValidatorService.validate
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // Mock reservationRepository.findByClientId
        when(reservationRepository.findByClientId(1L)).thenReturn(Collections.emptyList());

        // When & Then
        assertThrows(BusPriceException.class, () -> {
            reservationService.createReservation(reservation);
        });
    }

    @Test
    void testCreateReservationWithExistingReservation() {
        // Given
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), null);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(null, 1L, trips);

        // Mock existing reservation
        Reservation existingReservation = new Reservation(2L, 1L, trips);
        when(reservationRepository.findByClientId(1L)).thenReturn(List.of(existingReservation));

        // Mock reservationValidatorService.validate
        doNothing().when(reservationValidatorService).validate(any(Reservation.class));

        // When & Then
        assertThrows(ReservationAlreadyExistsException.class, () -> {
            reservationService.createReservation(reservation);
        });
    }

    @Test
    void testFindReservationsByClientId() {
        Long clientId = 1L;
        Trip trip = new Trip(null, "BUS123", LocalDateTime.now(), BigDecimal.ZERO);
        List<Trip> trips = List.of(trip);
        Reservation reservation = new Reservation(1L, clientId, trips);

        when(reservationRepository.findByClientId(clientId)).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

        // Validate reservations are fetched correctly
        assertFalse(reservations.isEmpty());
        assertEquals(clientId, reservations.getFirst().clientId());
    }

    @Test
    void testPayReservation() {
        // Given
        Long reservationId = 1L;
        String paymentType = "Credit Card";

        // Assuming paymentService.processPayment returns true
        when(paymentService.processPayment(reservationId, paymentType)).thenReturn(true);

        // Mock billRepository.save
        Bill billMock = new Bill(reservationId, paymentType);
        when(billRepository.save(any(Bill.class))).thenReturn(billMock);

        BillingService billingService = new BillingServiceImpl(paymentService, billRepository);

        // When
        Bill bill = billingService.payReservation(reservationId, paymentType);

        // Then
        verify(paymentService).processPayment(reservationId, paymentType);
        verify(billRepository).save(any(Bill.class));

        // Validate bill creation after successful payment
        assertNotNull(bill);
        assertEquals(reservationId, bill.reservationId());
        assertEquals(paymentType, bill.paymentType());
    }
}