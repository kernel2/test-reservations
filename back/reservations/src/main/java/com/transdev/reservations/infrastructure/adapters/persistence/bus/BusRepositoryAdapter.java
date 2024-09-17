package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import com.transdev.reservations.domain.exceptions.ResourceAlreadyExistsException;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripEntity;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripJpaRepository;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class BusRepositoryAdapter implements BusRepository {

    private final BusJpaRepository busJpaRepository;
    private final BusMapper busMapper;
    private final TripJpaRepository tripJpaRepository;
    private final TripMapper tripMapper;

    public BusRepositoryAdapter(BusJpaRepository busJpaRepository, BusMapper busMapper, TripJpaRepository tripJpaRepository, TripMapper tripMapper) {
        this.busJpaRepository = busJpaRepository;
        this.busMapper = busMapper;
        this.tripJpaRepository = tripJpaRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    @Transactional
    public Bus save(Bus bus) {
        if (busJpaRepository.findByBusNumber(bus.busNumber()).isPresent()) {
            throw new ResourceAlreadyExistsException("Bus with number " + bus.busNumber() + " already exists.");
        }
        BusEntity busEntity = busMapper.toEntity(bus);
        BusEntity savedEntity = busJpaRepository.save(busEntity);
        return busMapper.toDomainModel(savedEntity);
    }

    @Override
    public Bus findByNumber(String busNumber) {
        BusEntity busEntity = busJpaRepository.findByBusNumber(busNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found for busNumber: " + busNumber));
        return busMapper.toDomainModel(busEntity);
    }

    @Override
    public List<Bus> findAll() {
        return busJpaRepository.findAll()
                .stream()
                .map(busMapper::toDomainModel)
                .toList();
    }

    @Override
    public List<Trip> findTripsByBusAndDate(String busNumber, LocalDateTime dateStart, LocalDateTime dateEnd) {
        List<TripEntity> tripEntities = tripJpaRepository.findByBusNumberAndDateOfTravelBetween(busNumber, dateStart, dateEnd);
        return tripEntities.stream()
                .map(tripMapper::toDomainModel)
                .toList();
    }
}
