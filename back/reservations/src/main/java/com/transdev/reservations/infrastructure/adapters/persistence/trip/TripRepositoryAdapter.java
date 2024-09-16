package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.TripRepository;
import com.transdev.reservations.infrastructure.adapters.persistence.bus.BusEntity;
import com.transdev.reservations.infrastructure.adapters.persistence.bus.BusJpaRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Transactional
public class TripRepositoryAdapter implements TripRepository {

    private final TripJpaRepository tripJpaRepository;
    private final TripMapper tripMapper;
    private final BusJpaRepository busJpaRepository;

    public TripRepositoryAdapter(TripJpaRepository tripJpaRepository, TripMapper tripMapper, BusJpaRepository busJpaRepository) {
        this.tripJpaRepository = tripJpaRepository;
        this.tripMapper = tripMapper;
        this.busJpaRepository = busJpaRepository;
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        TripEntity entity = tripMapper.toEntity(trip);
        TripEntity savedEntity = tripJpaRepository.saveAndFlush(entity);
        return tripMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Trip> findById(Long id) {
        return tripJpaRepository.findById(id)
                .map(tripMapper::toDomainModel);
    }

    @Override
    public void deleteById(Long id) {
        tripJpaRepository.deleteById(id);
    }

    @Override
    public BigDecimal getBusPrice(String busNumber) {
        return busJpaRepository.findByBusNumber(busNumber)
                .map(BusEntity::getPricePerTrip)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public int getBusSeatsPerTrip(String busNumber) {
        return busJpaRepository.findByBusNumber(busNumber)
                .map(BusEntity::getSeatsPerTrip)
                .orElse(0);
    }
}