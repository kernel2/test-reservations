package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.ClientDTO;
import com.transdev.reservations.domain.model.Client;
import com.transdev.reservations.domain.ports.incoming.ClientService;
import com.transdev.reservations.infrastructure.adapters.persistence.client.ClientMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientApplicationService {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientApplicationService(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toDomainModel(clientDTO);
        Client createdClient = clientService.createClient(client);
        return clientMapper.toDTO(createdClient);
    }

    public Client getClientById(Long id) {
        return clientService.getClientById(id);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}