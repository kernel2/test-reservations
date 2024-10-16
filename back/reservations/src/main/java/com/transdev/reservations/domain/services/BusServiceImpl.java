package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public Bus getBusByNumber(String busNumber) {
        return busRepository.findByNumber(busNumber);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public List<Trip> getTripsByBusAndDate(String busNumber, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return busRepository.findTripsByBusAndDate(busNumber, dateStart, dateEnd);
    }
}