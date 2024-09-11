package com.transdev.reservations.domain.model;

import java.util.UUID;

public record Client(UUID id, String name, String email) {

}
