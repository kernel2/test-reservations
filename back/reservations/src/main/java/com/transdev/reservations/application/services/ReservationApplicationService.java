package com.transdev.reservations.application.services;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.infrastructure.adapters.persistence.reservation.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationApplicationService {

    private final ReservationService reservationService;
    private final BillingService billingService;
    private final ReservationMapper reservationMapper;

    public ReservationApplicationService(ReservationService reservationService, BillingService billingService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.billingService = billingService;
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

    public List<ReservationDTO> getReservationsByClientId(Long clientId) {
        List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);
        return reservationMapper.toDTOList(reservations);
    }

    public void cancelReservation(Long id) {
        reservationService.cancelReservation(id);
    }

    public Bill payReservation(Long reservationId, String paymentType) {
        return billingService.payReservation(reservationId, paymentType);
    }

    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }

    public ReservationDTO updateReservation(Long reservationId,ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toDomainModel(reservationDTO);
        Reservation updatedReservation = reservationService.updateReservation(reservationId,reservation);
        return reservationMapper.toDTO(updatedReservation);
    }
}