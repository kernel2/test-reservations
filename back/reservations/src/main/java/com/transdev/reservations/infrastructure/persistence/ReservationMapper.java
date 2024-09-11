package com.transdev.reservations.infrastructure.persistence;

import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.domain.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationDTO toDTO(Reservation reservation);

    Reservation toDomainModel(ReservationDTO reservationDTO);

    ReservationEntity toEntity(Reservation reservation);

    Reservation toDomainModel(ReservationEntity reservationEntity);

}