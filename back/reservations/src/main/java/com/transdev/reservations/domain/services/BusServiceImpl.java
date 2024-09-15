package com.transdev.reservations.domain.services;

import com.transdev.reservations.domain.exceptions.TripAlreadyExistsException;
import com.transdev.reservations.domain.model.Bus;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public Bus getBusByNumber(String busNumber) {
        return busRepository.findByNumber(busNumber);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public void createTripForBus(String busNumber, LocalDateTime travelDate) {
        // Vérifier si un trajet existe déjà pour ce bus à la date donnée
        if (busRepository.existsTripOnDate(busNumber, travelDate)) {
            throw new TripAlreadyExistsException("A trip already exists for bus " + busNumber + " on " + travelDate);
        }

        // Si aucune erreur, créer le trajet pour le bus
        // Logique métier à appliquer ici, ex. enregistrement du trajet, mise à jour du bus, etc.
    }
}