package com.transdev.reservations.adapters.outgoing;


import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean processPayment(Long reservationId, String paymentType) {
        // Implémentez la logique pour traiter le paiement
        return true; // Simuler le succès du paiement
    }
}