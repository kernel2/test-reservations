package com.transdev.reservations.infrastructure.adapters.persistence.reservation;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripEntity;
import com.transdev.reservations.infrastructure.adapters.persistence.trip.TripMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TripMapper.class}
)
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trips", source = "trips")
    Reservation toDomainModel(ReservationDTO reservationDTO);

    @AfterMapping
    default void setReservationInTrips(ReservationDTO reservationDTO, @MappingTarget ReservationEntity entity) {
        if (entity.getTrips() != null) {
            for (TripEntity tripEntity : entity.getTrips()) {
                tripEntity.setReservation(entity);
            }
        }
    }

    @Mapping(target = "trips", source = "trips")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(target = "trips", source = "trips")
    Reservation toDomainModel(ReservationEntity reservationEntity);

    @Mapping(target = "trips", source = "trips")
    ReservationEntity toEntity(Reservation reservation);

    @Mapping(target = "trips", source = "trips")
    List<ReservationDTO> toDTOList(List<Reservation> reservations);

    @Mapping(target = "trips", source = "trips")
    List<Reservation> toDomainModelList(List<ReservationEntity> reservationEntities);
}
