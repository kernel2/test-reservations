package com.transdev.reservations.domain.ports.outgoing;

public interface PaymentService {
    boolean processPayment(Long reservationId, String paymentType);
}