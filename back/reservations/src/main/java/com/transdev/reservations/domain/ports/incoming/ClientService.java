package com.transdev.reservations.domain.ports.incoming;

import com.transdev.reservations.domain.model.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
}