package com.transdev.reservations.infrastructure.adapters.persistence;

import com.transdev.reservations.domain.model.Client;
import com.transdev.reservations.domain.ports.outgoing.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    public ClientRepositoryAdapter(ClientJpaRepository clientJpaRepository, ClientMapper clientMapper) {
        this.clientJpaRepository = clientJpaRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = clientMapper.toEntity(client);
        ClientEntity savedEntity = clientJpaRepository.save(clientEntity);
        return clientMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id)
                .map(clientMapper::toDomainModel);
    }

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll().stream()
                .map(clientMapper::toDomainModel)
                .toList();
    }
}