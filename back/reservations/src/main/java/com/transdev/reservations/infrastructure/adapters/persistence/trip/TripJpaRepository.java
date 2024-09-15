package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripJpaRepository extends JpaRepository<TripEntity, Long> {
}
