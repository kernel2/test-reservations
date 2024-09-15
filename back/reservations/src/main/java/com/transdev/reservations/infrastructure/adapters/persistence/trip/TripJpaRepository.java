package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripJpaRepository extends JpaRepository<TripEntity, Long> {
    List<TripEntity> findByBusNumberAndDateOfTravelBetween(String busNumber, LocalDateTime dateStart, LocalDateTime dateEnd);
}
