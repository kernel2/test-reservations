package com.transdev.reservations.infrastructure.adapters.payment;

import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean processPayment(Long reservationId, String paymentType) {

        /* Implémentez la logique pour traiter le paiement, par exemple, interagir avec un API de paiement ou un système de traitement des paiements externe */
        return true;
    }
}