package com.transdev.reservations.infrastructure.adapters.rest;


import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/pay")
    public Bill payReservation(@RequestParam Long reservationId, @RequestParam String paymentType) {
        return billingService.payReservation(reservationId, paymentType);
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/sorted")
    public List<Bill> getBillsSortedByAmount() {
        return billingService.getBillsSortedByAmount();
    }
}