package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusJpaRepository extends JpaRepository<BusEntity, String> {
    Optional<BusEntity> findByBusNumber(String busNumber);
}