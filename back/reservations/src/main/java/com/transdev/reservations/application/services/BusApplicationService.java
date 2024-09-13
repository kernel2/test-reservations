package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.infrastructure.adapters.persistence.bus.BusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusApplicationService {
    private static final Logger log = LoggerFactory.getLogger(BusApplicationService.class);

    private final BusService busService;
    private final BusMapper busMapper;

    public BusApplicationService(BusService busService, BusMapper busMapper) {
        this.busService = busService;
        this.busMapper = busMapper;
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
                .collect(Collectors.toList());
    }
}