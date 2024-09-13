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

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "BillEntity{" +
                "reservationId=" + reservationId +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}
