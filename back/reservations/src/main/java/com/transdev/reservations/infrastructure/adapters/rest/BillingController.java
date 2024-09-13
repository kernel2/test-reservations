package com.transdev.reservations.infrastructure.adapters.rest;


import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(billingService.payReservation(reservationId, paymentType));
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
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