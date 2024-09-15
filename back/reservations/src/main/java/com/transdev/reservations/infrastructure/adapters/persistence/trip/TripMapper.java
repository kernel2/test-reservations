package com.transdev.reservations.infrastructure.adapters.persistence.trip;

import com.transdev.reservations.application.dto.TripDTO;
import com.transdev.reservations.domain.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TripMapper {

    @Mapping(target = "id", ignore = true)
    Trip toDomainModel(TripDTO tripDTO);

    TripDTO toDTO(Trip trip);

    Trip toDomainModel(TripEntity tripEntity);

    TripEntity toEntity(Trip trip);

    List<TripDTO> toDTOList(List<Trip> trips);

    List<Trip> toDomainModelList(List<TripEntity> tripEntities);
}