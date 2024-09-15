package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.ClientDTO;
import com.transdev.reservations.application.services.ClientApplicationService;
import com.transdev.reservations.domain.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {

    private final ClientApplicationService clientApplicationService;

    public ClientController(ClientApplicationService clientApplicationService) {
        this.clientApplicationService = clientApplicationService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientApplicationService.createClient(clientDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
            return ResponseEntity.ok(clientApplicationService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientApplicationService.getAllClients());
    }
}