package com.transdev.reservations.infrastructure.adapters.persistence.bus;

import com.transdev.reservations.application.dto.BusDTO;
import com.transdev.reservations.domain.model.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusMapper {

    @Mapping(target = "number", ignore = false)
    Bus toDomainModel(BusDTO busDTO);

    BusDTO toDTO(Bus bus);

    BusEntity toEntity(Bus bus);

    Bus toDomainModel(BusEntity busEntity);
}
