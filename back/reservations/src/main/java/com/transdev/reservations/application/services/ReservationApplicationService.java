package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.infrastructure.adapters.persistence.ReservationMapper;
import org.springframework.stereotype.Service;

@Service
public class ReservationApplicationService {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationApplicationService(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toDomainModel(reservationDTO);
        Reservation createdReservation = reservationService.createReservation(reservation);
        return reservationMapper.toDTO(createdReservation);
    }

    public ReservationDTO getReservation(Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return reservationMapper.toDTO(reservation);
    }

    public void cancelReservation(Long id) {
        reservationService.cancelReservation(id);
    }

    public BillDTO payReservation(Long reservationId, String paymentType) {
        Bill bill = reservationService.payReservation(reservationId, paymentType);
        return new BillDTO(bill.reservationId(), bill.paymentType());
    }

    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }
}