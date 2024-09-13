package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, PaymentService paymentService) {
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        Optional<Reservation> existingReservation = findExistingReservation(
                reservation.clientId(),
                reservation.busNumber(),
                reservation.dateOfTravel()
        );

        if (existingReservation.isPresent()) {
            throw new ReservationAlreadyExistsException("Le client a déjà réservé ce trajet pour cette date.");
        }

        // Check if any bus price is greater than 100 for discount
        // Assume getBusPrice() is a method in ReservationRepository to get the price
        BigDecimal busPrice = reservationRepository.getBusPrice(reservation.busNumber());
        boolean hasDiscount = busPrice.compareTo(new BigDecimal("100")) > 0;

        Reservation reservationWithDiscount = hasDiscount
                ? applyDiscount(reservation, busPrice)
                : reservation;
        return reservationRepository.save(reservationWithDiscount);
    }

    private Optional<Reservation> findExistingReservation(Long clientId, String busNumber, LocalDateTime dateOfTravel) {
        List<Reservation> existingReservations = reservationRepository.findByClientId(clientId);
        return existingReservations.stream()
                .filter(r -> r.busNumber().equals(busNumber) && r.dateOfTravel().equals(dateOfTravel))
                .findFirst();
    }

    private Reservation applyDiscount(Reservation reservation, BigDecimal busPrice) {
        // Assuming a 5% discount on the total price
        BigDecimal discountRate = new BigDecimal("0.05");
        BigDecimal discountAmount = busPrice.multiply(discountRate);
        BigDecimal discountedPrice = busPrice.subtract(discountAmount);

        // Return a new reservation with updated price
        return reservation.withPrice(discountedPrice);
    }

    @Override
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La réservation avec l'ID " + id + " n'a pas été trouvée."));
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = findReservationById(id);
        reservationRepository.delete(reservation);
    }

    @Override
    public Bill payReservation(Long reservationId, String paymentType) {
        boolean paymentSuccess = paymentService.processPayment(reservationId, paymentType);
        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }
        return new Bill(reservationId, paymentType);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findReservationsByClientId(Long clientId) {
        return reservationRepository.findByClientId(clientId);
    }
}
