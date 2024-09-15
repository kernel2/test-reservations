package com.transdev.reservations.domain.ports.outgoing;

import com.transdev.reservations.domain.model.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    BigDecimal applyDiscountToPrice(BigDecimal price);

    List<Trip> applyDiscountsToTrips(List<Trip> trips);
}