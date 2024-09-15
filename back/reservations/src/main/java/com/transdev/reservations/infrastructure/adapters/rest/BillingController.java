package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Bill> payReservation(@RequestParam Long reservationId, @RequestParam String paymentType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.payReservation(reservationId, paymentType));
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billingService.getAllBills());
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Bill>> getBillsSortedByAmount() {
        return ResponseEntity.ok(billingService.getBillsSortedByAmount());
    }
}