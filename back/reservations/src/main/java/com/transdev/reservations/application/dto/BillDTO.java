package com.transdev.reservations.application.dto;

public class BillDTO {
    private Long reservationId;
    private String paymentType;

    public BillDTO(Long reservationId, String paymentType) {
        this.reservationId = reservationId;
        this.paymentType = paymentType;
    }

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
}
