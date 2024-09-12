package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.application.services.ReservationApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationApplicationService reservationApplicationService;

    public ReservationController(ReservationApplicationService reservationApplicationService) {
        this.reservationApplicationService = reservationApplicationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationApplicationService.createReservation(reservationDTO);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationApplicationService.getReservation(id);
        return ResponseEntity.ok(reservationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationApplicationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
