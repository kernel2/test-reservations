package com.transdev.reservations.domain.model;

import java.util.UUID;

public record Bill(UUID reservationId, String paymentType) {
}
