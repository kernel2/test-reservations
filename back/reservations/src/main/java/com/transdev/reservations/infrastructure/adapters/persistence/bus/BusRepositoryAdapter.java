package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusRepositoryAdapter implements BusRepository {

    private final BusJpaRepository busJpaRepository;
    private final BusMapper busMapper;

    public BusRepositoryAdapter(BusJpaRepository busJpaRepository, BusMapper busMapper) {
        this.busJpaRepository = busJpaRepository;
        this.busMapper = busMapper;
    }

    @Override
    public Bus save(Bus bus) {
        BusEntity busEntity = busMapper.toEntity(bus);
        BusEntity savedEntity = busJpaRepository.save(busEntity);
        return busMapper.toDomainModel(savedEntity);
    }

    @Override
    public Bus findByNumber(String busNumber) {
        BusEntity busEntity = busJpaRepository.findByBusNumber(busNumber)
                .orElseThrow(() -> new RuntimeException("Bus not found for busNumber: " + busNumber));
        return busMapper.toDomainModel(busEntity);
    }

    @Override
    public List<Bus> findAll() {
        return busJpaRepository.findAll()
                .stream()
                .map(busMapper::toDomainModel)
                .collect(Collectors.toList());
    }
}
