package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository  {
    Bill save(Bill bill);
    List<Bill> findAll();
    Optional<Bill> findByReservationId(Long reservationId);

}
