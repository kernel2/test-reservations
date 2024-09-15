package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.BusPriceException;
import com.transdev.reservations.domain.model.Trip;
import com.transdev.reservations.domain.ports.outgoing.DiscountService;
import com.transdev.reservations.domain.ports.outgoing.TripRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("100");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05");

    private final TripRepository tripRepository;

    public DiscountServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public BigDecimal applyDiscountToPrice(BigDecimal price) {
        boolean hasDiscount = price.compareTo(DISCOUNT_THRESHOLD) > 0;

        if (hasDiscount) {
            return applyDiscount(price);
        } else {
            return price.setScale(2, RoundingMode.HALF_UP); // Assure 2 décimales
        }
    }

    @Override
    public List<Trip> applyDiscountsToTrips(List<Trip> trips) {
        return trips.stream()
                .map(this::applyDiscountToTrip)
                .toList();
    }

    private Trip applyDiscountToTrip(Trip trip) {
        BigDecimal busPrice = getBusPrice(trip.busNumber());
        BigDecimal discountedPrice = applyDiscountToPrice(busPrice);
        return new Trip(trip.id(), trip.busNumber(), trip.dateOfTravel(), discountedPrice);
    }

    private BigDecimal getBusPrice(String busNumber) {
        BigDecimal busPrice = tripRepository.getBusPrice(busNumber);
        if (busPrice == null || busPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusPriceException("Le prix du bus est introuvable ou invalide pour le numéro de bus : " + busNumber);
        }
        return busPrice;
    }

    private BigDecimal applyDiscount(BigDecimal price) {
        BigDecimal discountAmount = price.multiply(DISCOUNT_RATE);
        discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountedPrice = price.subtract(discountAmount);
        discountedPrice = discountedPrice.setScale(2, RoundingMode.HALF_UP);
        return discountedPrice;
    }
}