package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;

public class ReservationServiceImpl implements ReservationService {

    private final PaymentService paymentService;

    public ReservationServiceImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        // Logique pour créer une réservation
        // Vérifiez la disponibilité du bus, etc.
        return reservation;
    }

    @Override
    public Reservation findReservationById(Long id) {
        // Logique pour trouver une réservation par ID
        //return new Reservation("","","",""); // Remplacez par la recherche réelle
        return null;
    }

    @Override
    public void cancelReservation(Long id) {
        // Logique pour annuler une réservation
    }

    @Override
    public Bill payReservation(Long reservationId, String paymentType) {
        boolean paymentSuccess = paymentService.processPayment(reservationId, paymentType);
        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }
        // Créez une facture en fonction du résultat du paiement
        return new Bill(reservationId, paymentType);
    }

    @Override
    public void deleteReservation(Long id) {
    }
}
