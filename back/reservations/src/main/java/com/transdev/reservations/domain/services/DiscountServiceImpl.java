package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.model.Reservation;
import com.transdev.reservations.domain.ports.outgoing.DiscountService;

import java.math.BigDecimal;

public class DiscountServiceImpl implements DiscountService {

    private static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("100");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05");

    @Override
    public Reservation applyDiscountToReservation(Reservation reservation, BigDecimal busPrice) {
        // Determine if the discount should be applied
        boolean hasDiscount = busPrice.compareTo(DISCOUNT_THRESHOLD) > 0;

        // Calculate the discounted price if applicable
        BigDecimal finalPrice;
        if (hasDiscount) {
            finalPrice = applyDiscount(busPrice);
        } else {
            finalPrice = (reservation.price() == null || reservation.price().compareTo(BigDecimal.ZERO) <= 0) ? busPrice : reservation.price();
        }

        // Return a new reservation with the adjusted price
        return new Reservation(reservation.id(), reservation.dateOfTravel(), reservation.busNumber(), reservation.clientId(), finalPrice);
    }

    private BigDecimal applyDiscount(BigDecimal price) {
        BigDecimal discountAmount = price.multiply(DISCOUNT_RATE);
        return price.subtract(discountAmount);
    }
}