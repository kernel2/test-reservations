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
        // Logique métier pour traiter une facture
        Bill bill = new Bill(reservationId, paymentType);
        // Appel au service de paiement
        paymentService.processPayment(reservationId, paymentType);
        // Sauvegarde dans le référentiel
        return billRepository.save(bill);
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