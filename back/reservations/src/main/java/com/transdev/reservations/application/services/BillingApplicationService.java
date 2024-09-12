package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.infrastructure.adapters.persistence.bill.BillMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingApplicationService {

    private final BillingService billingService;
    private final BillMapper billMapper;

    public BillingApplicationService(BillingService billingService, BillMapper billMapper) {
        this.billingService = billingService;
        this.billMapper = billMapper;
    }

    public BillDTO payReservation(Long reservationId, String paymentType) {
        Bill bill = billingService.payReservation(reservationId, paymentType);
        return billMapper.toDTO(bill);
    }

    public List<BillDTO> getAllBills() {
        return billingService.getAllBills().stream()
                .map(billMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BillDTO> getBillsSortedByAmount() {
        return billingService.getBillsSortedByAmount().stream()
                .map(billMapper::toDTO)
                .collect(Collectors.toList());
    }
}