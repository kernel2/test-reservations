package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.exceptions.InvalidReservationException;
import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.model.ReservationValidator;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        ReservationValidator.validate(reservation);
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

        if (busPrice == null || busPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusPriceException("Le prix du bus est introuvable ou NULL ou 0.00 pour le numéro de bus : " + reservation.busNumber());
        }

        boolean hasDiscount = busPrice.compareTo(new BigDecimal("100")) > 0;

        Reservation reservationWithDiscount;
        if (hasDiscount) {
            reservationWithDiscount = applyDiscount(reservation, busPrice);
        } else {
            if (reservation.price() == null || reservation.price().compareTo(BigDecimal.ZERO) <= 0) {
                reservationWithDiscount = new Reservation(reservation.id(), reservation.dateOfTravel(), reservation.busNumber(), reservation.clientId(), busPrice);
            } else {
                reservationWithDiscount = reservation;
            }
        }

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
        return new Reservation(reservation.id(), reservation.dateOfTravel(),
                reservation.busNumber(), reservation.clientId(), discountedPrice);
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
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findReservationsByClientId(Long clientId) {
        return reservationRepository.findByClientId(clientId);
    }
}
