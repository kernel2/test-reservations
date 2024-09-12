package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.application.services.BusApplicationService;
import com.transdev.reservations.domain.model.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(busApplicationService.createBus(busDTO));
    }

    @GetMapping("/{number}")
    public ResponseEntity<BusDTO> getBusByNumber(@PathVariable String number) {
        return ResponseEntity.ok(busApplicationService.getBusByNumber(number));
    }

    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.ok(busApplicationService.getAllBuses());
    }
}