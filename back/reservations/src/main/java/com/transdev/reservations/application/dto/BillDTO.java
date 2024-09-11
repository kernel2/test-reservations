package com.transdev.reservations.application.dto;

public class BillDTO {
    private Long reservationId;
    private String paymentType;

    public BillDTO(Long reservationId, String paymentType) {
        this.reservationId = reservationId;
        this.paymentType = paymentType;
    }

}
