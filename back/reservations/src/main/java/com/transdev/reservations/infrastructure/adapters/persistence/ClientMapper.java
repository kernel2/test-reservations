package com.transdev.reservations.infrastructure.adapters.persistence;

import com.transdev.reservations.application.dto.ClientDTO;
import com.transdev.reservations.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    @Mapping(target = "id", ignore = false)
    Client toDomainModel(ClientDTO clientDTO);

    ClientDTO toDTO(Client client);

    ClientEntity toEntity(Client client);

    Client toDomainModel(ClientEntity clientEntity);
}