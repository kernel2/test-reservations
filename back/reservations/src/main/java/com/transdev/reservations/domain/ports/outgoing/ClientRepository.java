package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
}