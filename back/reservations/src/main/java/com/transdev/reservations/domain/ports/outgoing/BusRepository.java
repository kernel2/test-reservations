package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Bus;

import java.util.List;

public interface BusRepository {
    Bus save(Bus bus);
    Bus findByNumber(String number);
    List<Bus> findAll();
}