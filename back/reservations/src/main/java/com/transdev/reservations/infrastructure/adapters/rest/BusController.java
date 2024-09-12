package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.application.services.BusApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    private static final Logger log = LoggerFactory.getLogger(BusController.class);

    private final BusApplicationService busApplicationService;

    public BusController(BusApplicationService busApplicationService) {
        this.busApplicationService = busApplicationService;
    }

    @PostMapping
    public ResponseEntity<BusDTO> createBus(@RequestBody BusDTO busDTO) {
        return ResponseEntity.ok(busApplicationService.createBus(busDTO));
    }

    @GetMapping("/{busNumber}")
    public ResponseEntity<BusDTO> getBusByNumber(@PathVariable String busNumber) {
        return ResponseEntity.ok(busApplicationService.getBusByNumber(busNumber));
    }

    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.ok(busApplicationService.getAllBuses());
    }
}