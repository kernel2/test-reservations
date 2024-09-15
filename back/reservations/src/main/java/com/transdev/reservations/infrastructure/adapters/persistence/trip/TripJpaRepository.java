package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripJpaRepository extends JpaRepository<TripEntity, Long> {
    boolean existsByBusNumberAndDateOfTravel(String busNumber, LocalDateTime travelDate);

    List<TripEntity> findByBusNumberAndDateOfTravel(String busNumber, LocalDateTime travelDate);
}
