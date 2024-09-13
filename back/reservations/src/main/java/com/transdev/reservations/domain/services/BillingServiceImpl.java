package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.outgoing.BillRepository;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

public class BillingServiceImpl implements BillingService {

    private final PaymentService paymentService;
    private final BillRepository billRepository;

    public BillingServiceImpl(PaymentService paymentService, BillRepository billRepository) {
        this.paymentService = paymentService;
        this.billRepository = billRepository;
    }

    @Override
    public Bill payReservation(Long reservationId, String paymentType) {
        Bill bill = new Bill(reservationId, paymentType);
        boolean paymentSuccess = paymentService.processPayment(reservationId, paymentType);
        if (paymentSuccess) {
            return billRepository.save(bill);
        } else {
            throw new IllegalArgumentException("Payment failed");
        }
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> getBillsSortedByAmount() {
        return billRepository.findAll().stream()
                .sorted((b1, b2) -> b1.reservationId().compareTo(b2.reservationId())) // Trier par identifiant
                .collect(Collectors.toList());
    }
}