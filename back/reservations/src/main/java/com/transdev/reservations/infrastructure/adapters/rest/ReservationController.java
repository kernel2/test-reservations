package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.application.services.ReservationApplicationService;
import com.transdev.reservations.domain.exceptions.ReservationAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationApplicationService reservationApplicationService;

    public ReservationController(ReservationApplicationService reservationApplicationService) {
        this.reservationApplicationService = reservationApplicationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO createdReservation = reservationApplicationService.createReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (ReservationAlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationApplicationService.getReservation(id);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByClientId(@PathVariable Long clientId) {
        List<ReservationDTO> reservations = reservationApplicationService.getReservationsByClientId(clientId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationApplicationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
