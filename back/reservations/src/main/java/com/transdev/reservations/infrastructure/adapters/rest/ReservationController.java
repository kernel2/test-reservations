package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.application.services.ReservationApplicationService;
import com.transdev.reservations.domain.exceptions.*;
import com.transdev.reservations.domain.model.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {


    private final ReservationApplicationService reservationApplicationService;

    public ReservationController(ReservationApplicationService reservationApplicationService) {
        this.reservationApplicationService = reservationApplicationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO createdReservation = reservationApplicationService.createReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
        }catch (InvalidReservationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de validation : " + ex.getMessage());
        } catch (ReservationAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit : " + ex.getMessage());
        } catch (BusPriceException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de prix du bus : " + ex.getMessage());
        } catch (UnexpectedErrorException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur : " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        try {
            ReservationDTO reservationDTO = reservationApplicationService.getReservation(id);
            return ResponseEntity.ok(reservationDTO);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByClientId(@PathVariable Long clientId) {
        List<ReservationDTO> reservations = reservationApplicationService.getReservationsByClientId(clientId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationApplicationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        try {
            reservationApplicationService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping("{reservationId}/pay/{paymentType}")
    public ResponseEntity<BillDTO> payReservation(@PathVariable Long reservationId, @PathVariable String paymentType) {
        try {
            Bill bill = reservationApplicationService.payReservation(reservationId, paymentType);
            BillDTO billDTO = new BillDTO(bill.reservationId(), bill.paymentType());
            return ResponseEntity.ok(billDTO);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
