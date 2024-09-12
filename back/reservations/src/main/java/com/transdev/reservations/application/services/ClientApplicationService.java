package com.transdev.reservations.application.services;

import com.transdev.reservations.domain.model.Client;
import com.transdev.reservations.domain.ports.incoming.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientApplicationService {

    private final ClientService clientService;

    public ClientApplicationService(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client createClient(Client client) {
        return clientService.createClient(client);
    }

    public Client getClientById(Long id) {
        return clientService.getClientById(id);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}