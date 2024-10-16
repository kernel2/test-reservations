package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.application.dto.TripDTO;
import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.infrastructure.adapters.persistence.bus.BusMapper;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusApplicationService {

    private final BusService busService;
    private final BusMapper busMapper;
    private final TripMapper tripMapper;

    public BusApplicationService(BusService busService, BusMapper busMapper, TripMapper tripMapper) {
        this.busService = busService;
        this.busMapper = busMapper;
        this.tripMapper = tripMapper;
    }

    public BusDTO createBus(BusDTO busDTO) {
        Bus bus = busMapper.toDomainModel(busDTO);
        Bus createdBus = busService.createBus(bus);
        return busMapper.toDTO(createdBus);
    }

    public BusDTO getBusByNumber(String busNumber) {
        Bus bus = busService.getBusByNumber(busNumber);
        return busMapper.toDTO(bus);
    }

    public List<BusDTO> getAllBuses() {
        return busService.getAllBuses().stream()
                .map(busMapper::toDTO)
                .toList();
    }

    public List<TripDTO> getTripsByBusAndDate(String busNumber, LocalDateTime dateStart, LocalDateTime dateEnd) {
        List<Trip> trips = busService.getTripsByBusAndDate(busNumber, dateStart, dateEnd);
        return trips.stream()
                .map(tripMapper::toDTO)
                .toList();
    }
}