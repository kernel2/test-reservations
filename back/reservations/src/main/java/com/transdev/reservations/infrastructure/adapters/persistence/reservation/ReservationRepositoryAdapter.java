package com.transdev.reservations.infrastructure.adapters.persistence.reservation;

import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ReservationRepositoryAdapter implements ReservationRepository {
    private static final Logger log = LoggerFactory.getLogger(ReservationRepositoryAdapter.class);

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationMapper reservationMapper;

    public ReservationRepositoryAdapter(ReservationJpaRepository reservationJpaRepository, ReservationMapper reservationMapper) {
        this.reservationJpaRepository = reservationJpaRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    @Transactional
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = reservationMapper.toEntity(reservation);
        ReservationEntity savedEntity = reservationJpaRepository.save(entity);
        return reservationMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationJpaRepository.findById(id)
                .map(reservationMapper::toDomainModel);
    }

    @Override
    public void delete(Reservation reservation) {
        ReservationEntity entity = reservationMapper.toEntity(reservation);
        reservationJpaRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        reservationJpaRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findByClientId(Long clientId) {
        List<ReservationEntity> entities = reservationJpaRepository.findByClientId(clientId);
        return reservationMapper.toDomainModelList(entities);
    }
}