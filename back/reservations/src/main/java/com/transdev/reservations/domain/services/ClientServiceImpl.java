package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Client;
import com.transdev.reservations.domain.ports.incoming.ClientService;
import com.transdev.reservations.domain.ports.outgoing.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        // Business logic if any
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}