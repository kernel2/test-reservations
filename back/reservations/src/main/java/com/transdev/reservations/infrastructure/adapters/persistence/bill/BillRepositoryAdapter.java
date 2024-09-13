package com.transdev.reservations.infrastructure.adapters.persistence.bill;

import com.transdev.reservations.domain.model.Bill;
import com.transdev.reservations.domain.ports.outgoing.BillRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class BillRepositoryAdapter implements BillRepository {

    private final BillJpaRepository billJpaRepository;
    private final BillMapper billMapper;

    public BillRepositoryAdapter(BillJpaRepository billJpaRepository, BillMapper billMapper) {
        this.billJpaRepository = billJpaRepository;
        this.billMapper = billMapper;
    }

    @Override
    @Transactional
    public Bill save(Bill bill) {
        var billEntity = billMapper.toEntity(bill);
        var savedEntity = billJpaRepository.save(billEntity);
        return billMapper.toDomain(savedEntity);
    }

    @Override
    public List<Bill> findAll() {
        return billJpaRepository.findAll().stream()
                .map(billMapper::toDomain)
                .collect(Collectors.toList());
    }
}
