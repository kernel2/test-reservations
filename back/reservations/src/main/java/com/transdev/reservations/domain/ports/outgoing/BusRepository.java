package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.model.Trip;

import java.time.LocalDateTime;
import java.util.List;

public interface BusRepository {
    Bus save(Bus bus);
    Bus findByNumber(String busNumber);
    List<Bus> findAll();

    boolean existsTripOnDate(String busNumber, LocalDateTime travelDate);
    List<Trip> findTripsByBusAndDate(String busNumber, LocalDateTime travelDate);

}