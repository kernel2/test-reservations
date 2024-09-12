package com.transdev.reservations.infrastructure.adapters.persistence.bill;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bills")
public class BillEntity {

    @Id
    private Long reservationId;
    private String paymentType;

    public Long getReservationId() {
        return reservationId;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
