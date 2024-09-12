package com.transdev.reservations.infrastructure.adapters.persistence;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mapping(target = "id", ignore = false)
    Reservation toDomainModel(ReservationDTO reservationDTO);

    ReservationDTO toDTO(Reservation reservation);

    ReservationEntity toEntity(Reservation reservation);

    Reservation toDomainModel(ReservationEntity reservationEntity);

}