package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.DiscountService;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;
import com.transdev.reservations.domain.ports.outgoing.ReservationValidatorService;
import com.transdev.reservations.domain.ports.outgoing.TripRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;
    private final ReservationValidatorService reservationValidatorService;
    private final DiscountService discountService;


    public ReservationServiceImpl(ReservationRepository reservationRepository, TripRepository tripRepository, ReservationValidatorService reservationValidatorService, DiscountService discountService) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.reservationValidatorService = reservationValidatorService;
        this.discountService = discountService;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        reservationValidatorService.validate(reservation);

        checkForExistingReservation(reservation);

        List<Trip> tripsWithDiscount = discountService.applyDiscountsToTrips(reservation.trips());

        Reservation reservationWithDiscount = new Reservation(
                reservation.id(),
                reservation.clientId(),
                tripsWithDiscount
        );

        return reservationRepository.save(reservationWithDiscount);
    }

    private void checkForExistingReservation(Reservation reservation) {
        for (Trip trip : reservation.trips()) {
            Optional<Reservation> existingReservation = findExistingReservation(
                    reservation.clientId(),
                    trip.busNumber(),
                    trip.dateOfTravel()
            );

            if (existingReservation.isPresent()) {
                throw new ReservationAlreadyExistsException("Le client a déjà réservé ce trajet pour cette date.");
            }
        }
    }

    private Optional<Reservation> findExistingReservation(Long clientId, String busNumber, LocalDateTime dateOfTravel) {
        List<Reservation> existingReservations = reservationRepository.findByClientId(clientId);
        return existingReservations.stream()
                .filter(r -> r.trips().stream()
                        .anyMatch(trip -> trip.busNumber().equals(busNumber) && trip.dateOfTravel().equals(dateOfTravel))
                )
                .findFirst();
    }

    @Override
    public Reservation findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La réservation avec l'ID " + id + " n'a pas été trouvée."));
        List<Trip> trips = reservation.trips().stream()
                .map(trip -> new Trip(
                        trip.id(),
                        trip.busNumber(),
                        trip.dateOfTravel(),
                        tripRepository.getBusSeatsPerTrip(trip.busNumber()),
                        trip.price()))
                .toList();
        return new Reservation(reservation.id(), reservation.clientId(), trips);
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

    @Override
    public Reservation updateReservation(Long reservationId, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("La réservation avec l'ID " + reservationId + " n'a pas été trouvée."));

        List<Trip> updatedTrips = existingReservation.trips().stream()
                .map(existingTrip -> {
                    Optional<Trip> newTripOpt = reservation.trips().stream()
                            .filter(newTrip -> newTrip.id() != null && newTrip.id().equals(existingTrip.id()))  // Vérification de l'ID non nul avant comparaison
                            .findFirst();

                    if (newTripOpt.isPresent()) {
                        Trip newTrip = newTripOpt.get();
                        return new Trip(
                                existingTrip.id(),
                                newTrip.busNumber(),
                                newTrip.dateOfTravel(),
                                newTrip.seatsPerTrip(),
                                newTrip.price()
                        );
                    } else {
                        return existingTrip;
                    }
                })
                .toList();

        Reservation updatedReservation = new Reservation(
                existingReservation.id(),
                existingReservation.clientId(),
                updatedTrips
        );

        return reservationRepository.save(updatedReservation);
    }
}
