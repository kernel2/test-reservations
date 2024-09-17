package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.PaymentAlreadyProcessedException;
import com.transdev.reservations.domain.exceptions.PaymentFailedException;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.outgoing.BillRepository;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;

import java.util.List;
import java.util.Optional;

public class BillingServiceImpl implements BillingService {

    private final PaymentService paymentService;
    private final BillRepository billRepository;

    public BillingServiceImpl(PaymentService paymentService, BillRepository billRepository) {
        this.paymentService = paymentService;
        this.billRepository = billRepository;
    }

    @Override
    public Bill payReservation(Long reservationId, String paymentType) {
        Optional<Bill> existingBill = billRepository.findByReservationId(reservationId);
        if (existingBill.isPresent()) {
            throw new PaymentAlreadyProcessedException("La réservation a déjà été payée.");
        }
        boolean paymentSuccess = paymentService.processPayment(reservationId, paymentType);
        if (paymentSuccess) {
            Bill bill = new Bill(reservationId, paymentType);
            return billRepository.save(bill);
        } else {
            throw new PaymentFailedException("Le paiement a échoué. Veuillez réessayer.");
        }
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> getBillsSortedByAmount() {
        return billRepository.findAll().stream()
                .sorted((b1, b2) -> b1.reservationId().compareTo(b2.reservationId()))
                .toList();
    }
}