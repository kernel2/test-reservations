package com.transdev.reservations.infrastructure.adapters.persistence.bill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillJpaRepository extends JpaRepository<BillEntity, String> {
}