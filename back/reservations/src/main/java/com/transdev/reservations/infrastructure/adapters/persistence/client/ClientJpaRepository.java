package com.transdev.reservations.infrastructure.adapters.persistence.client;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {
}