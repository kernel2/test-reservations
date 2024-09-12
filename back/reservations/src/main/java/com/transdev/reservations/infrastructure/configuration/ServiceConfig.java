package com.transdev.reservations.infrastructure.configuration;

import com.transdev.reservations.domain.ports.incoming.ClientService;
import com.transdev.reservations.domain.ports.outgoing.ClientRepository;
import com.transdev.reservations.domain.services.ClientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    private final ClientRepository clientRepository;

    public ServiceConfig(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(clientRepository);
    }
}