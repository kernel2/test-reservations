package com.transdev.reservations.infrastructure.adapters.persistence.reservation;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    Reservation toDomainModel(ReservationDTO reservationDTO);

    ReservationDTO toDTO(Reservation reservation);

    ReservationEntity toEntity(Reservation reservation);

    Reservation toDomainModel(ReservationEntity reservationEntity);

    List<ReservationDTO> toDTOList(List<Reservation> reservations);

    List<Reservation> toDomainModelList(List<ReservationEntity> reservationEntities);
}