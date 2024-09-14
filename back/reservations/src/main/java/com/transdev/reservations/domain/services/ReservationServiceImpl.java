package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.DiscountService;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;
import com.transdev.reservations.domain.ports.outgoing.ReservationValidatorService;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationValidatorService reservationValidatorService;
    private final DiscountService discountService;


    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationValidatorService reservationValidatorService, DiscountService discountService) {
        this.reservationRepository = reservationRepository;
        this.reservationValidatorService = reservationValidatorService;
        this.discountService = discountService;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        //Check Reservation Fields
        reservationValidatorService.validate(reservation);

        // Check for existing reservation
        checkForExistingReservation(reservation);

        // Check if any bus price is greater than 100 for discount
        // Assume getBusPrice() is a method in ReservationRepository to get the price
        // Fetch bus price
        BigDecimal busPrice = getBusPrice(reservation.busNumber());

        // Apply discount if applicable
        Reservation reservationWithDiscount = discountService.applyDiscountToReservation(reservation, busPrice);

        return reservationRepository.save(reservationWithDiscount);
    }

    private void checkForExistingReservation(Reservation reservation) {
        Optional<Reservation> existingReservation = findExistingReservation(
                reservation.clientId(),
                reservation.busNumber(),
                reservation.dateOfTravel()
        );

        if (existingReservation.isPresent()) {
            throw new ReservationAlreadyExistsException("Le client a déjà réservé ce trajet pour cette date.");
        }
    }

    private BigDecimal getBusPrice(String busNumber) {
        BigDecimal busPrice = reservationRepository.getBusPrice(busNumber);
        if (busPrice == null || busPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusPriceException("Le prix du bus est introuvable ou invalide pour le numéro de bus : " + busNumber);
        }
        return busPrice;
    }

    private Optional<Reservation> findExistingReservation(Long clientId, String busNumber, LocalDateTime dateOfTravel) {
        List<Reservation> existingReservations = reservationRepository.findByClientId(clientId);
        return existingReservations.stream()
                .filter(r -> r.busNumber().equals(busNumber) && r.dateOfTravel().equals(dateOfTravel))
                .findFirst();
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
