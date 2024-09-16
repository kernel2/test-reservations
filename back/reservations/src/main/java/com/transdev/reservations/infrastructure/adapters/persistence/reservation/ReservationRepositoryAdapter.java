package com.transdev.reservations.infrastructure.adapters.persistence.reservation;

import com.transdev.reservations.domain.exceptions.ResourceNotFoundException;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.ReservationRepository;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripEntity;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripJpaRepository;
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
    private final TripJpaRepository tripJpaRepository;
    private final ReservationMapper reservationMapper;

    public ReservationRepositoryAdapter(ReservationJpaRepository reservationJpaRepository, TripJpaRepository tripJpaRepository, ReservationMapper reservationMapper) {
        this.reservationJpaRepository = reservationJpaRepository;
        this.tripJpaRepository = tripJpaRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    @Transactional
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = reservationMapper.toEntity(reservation);

        if (entity.getTrips() != null) {
            for (TripEntity tripEntity : entity.getTrips()) {
                tripEntity.setReservation(entity);

                if (tripEntity.getId() == null) {
                    log.info("Nouveau trip ajouté à la réservation.");
                } else {
                    TripEntity existingTripEntity = tripJpaRepository.findById(tripEntity.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Trip ID " + tripEntity.getId() + " introuvable."));

                    existingTripEntity.setBusNumber(tripEntity.getBusNumber());
                    existingTripEntity.setDateOfTravel(tripEntity.getDateOfTravel());
                    existingTripEntity.setSeatsPerTrip(tripEntity.getSeatsPerTrip());
                    existingTripEntity.setPrice(tripEntity.getPrice());

                    tripJpaRepository.save(existingTripEntity);
                }
            }
        }

        ReservationEntity savedEntity = reservationJpaRepository.saveAndFlush(entity);
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