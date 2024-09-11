package com.transdev.reservations.infrastructure.persistence;

import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.domain.model.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BillMapper {

    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    BillDTO toDTO(Bill bill);

    Bill toDomainModel(BillDTO billDTO);
}