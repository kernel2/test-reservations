package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.application.dto.TripDTO;
import com.transdev.reservations.application.services.BusApplicationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BusController {

    private final BusApplicationService busApplicationService;

    public BusController(BusApplicationService busApplicationService) {
        this.busApplicationService = busApplicationService;
    }

    @PostMapping
    public ResponseEntity<BusDTO> createBus(@RequestBody BusDTO busDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busApplicationService.createBus(busDTO));
    }

    @GetMapping("/{busNumber}")
    public ResponseEntity<BusDTO> getBusByNumber(@PathVariable String busNumber) {
        return ResponseEntity.ok(busApplicationService.getBusByNumber(busNumber));
    }

    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.ok(busApplicationService.getAllBuses());
    }

    @GetMapping("/{busNumber}/trips")
    public ResponseEntity<List<TripDTO>> getTripsByBusAndDate(
            @PathVariable String busNumber,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        LocalDateTime dateStart = travelDate.atStartOfDay();
        LocalDateTime dateEnd = travelDate.atTime(23, 59, 59); // End of the day
        List<TripDTO> trips = busApplicationService.getTripsByBusAndDate(busNumber, dateStart, dateEnd);
        return ResponseEntity.ok(trips);
    }
}