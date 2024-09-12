package com.transdev.reservations.domain.ports.incoming;

import com.transdev.reservations.domain.model.Bus;

import java.util.List;

public interface BusService {
    Bus createBus(Bus bus);
    Bus getBusByNumber(String number);
    List<Bus> getAllBuses();
}