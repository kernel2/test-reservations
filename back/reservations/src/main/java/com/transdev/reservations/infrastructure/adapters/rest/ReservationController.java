package com.transdev.reservations.infrastructure.adapters.rest;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.application.services.ReservationApplicationService;
import com.transdev.reservations.domain.model.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {


    private final ReservationApplicationService reservationApplicationService;

    public ReservationController(ReservationApplicationService reservationApplicationService) {
        this.reservationApplicationService = reservationApplicationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationApplicationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
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

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationApplicationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{reservationId}/pay/{paymentType}")
    public ResponseEntity<BillDTO> payReservation(@PathVariable Long reservationId, @PathVariable String paymentType) {
        Bill bill = reservationApplicationService.payReservation(reservationId, paymentType);
        BillDTO billDTO = new BillDTO(bill.reservationId(), bill.paymentType());
        return ResponseEntity.ok(billDTO);
    }
}
