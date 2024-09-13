package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.application.services.BusApplicationService;
import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusApplicationService busApplicationService;

    public BusController(BusApplicationService busApplicationService) {
        this.busApplicationService = busApplicationService;
    }

    @PostMapping
    public ResponseEntity<BusDTO> createBus(@RequestBody BusDTO busDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(busApplicationService.createBus(busDTO));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }    }

    @GetMapping("/{busNumber}")
    public ResponseEntity<BusDTO> getBusByNumber(@PathVariable String busNumber) {
        try {
            return ResponseEntity.ok(busApplicationService.getBusByNumber(busNumber));
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }    }

    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.ok(busApplicationService.getAllBuses());
    }
}