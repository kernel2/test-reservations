package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Bill;

import java.util.List;

public interface BillRepository  {
    Bill save(Bill bill);
    List<Bill> findAll();
}
