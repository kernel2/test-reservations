package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusJpaRepository extends JpaRepository<BusEntity, String> {

}