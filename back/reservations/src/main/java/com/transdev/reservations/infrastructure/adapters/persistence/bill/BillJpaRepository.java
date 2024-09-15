package com.transdev.reservations.infrastructure.adapters.persistence.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillJpaRepository extends JpaRepository<BillEntity, String> {
    Optional<BillEntity> findByReservationId(Long reservationId);
}