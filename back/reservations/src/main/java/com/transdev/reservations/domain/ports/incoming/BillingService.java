package com.transdev.reservations.domain.ports.incoming;

import com.transdev.reservations.domain.model.Bill;

import java.util.List;

public interface BillingService {
    Bill payReservation(Long reservationId, String paymentType);
    List<Bill> getAllBills();
    List<Bill> getBillsSortedByAmount();
}