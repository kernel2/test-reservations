package com.transdev.reservations.infrastructure.adapters.persistence.bill;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.domain.model.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {

    @Mapping(target = "reservationId", ignore = false)
    Bill toDomainModel(BillDTO billDTO);

    BillDTO toDTO(Bill bill);

    BillEntity toEntity(Bill bill);
    Bill toDomain(BillEntity billEntity);

}