package com.transdev.reservations.infrastructure.configuration;

import com.transdev.reservations.domain.services.ReservationValidatorServiceImpl;
import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.domain.ports.incoming.ClientService;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.*;
import com.transdev.reservations.domain.services.*;
import com.transdev.reservations.infrastructure.adapters.payment.PaymentServiceImpl;
import com.transdev.reservations.infrastructure.adapters.persistence.reservation.ReservationJpaRepository;
import com.transdev.reservations.infrastructure.adapters.persistence.reservation.ReservationMapper;
import com.transdev.reservations.infrastructure.adapters.persistence.reservation.ReservationRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository, TripRepository tripRepository, ReservationValidatorService reservationValidatorService, DiscountService discountService) {
        return new ReservationServiceImpl(reservationRepository, tripRepository,reservationValidatorService, discountService);
    }

    @Bean
    public ReservationRepository reservationRepository(ReservationJpaRepository reservationJpaRepository, ReservationMapper reservationMapper) {
        return new ReservationRepositoryAdapter(reservationJpaRepository, reservationMapper);
    }

    @Bean
    public PaymentService paymentService() {
        return new PaymentServiceImpl();
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }

    @Bean
    public BusService busService(BusRepository busRepository) {
        return new BusServiceImpl(busRepository);
    }

    @Bean
    public BillingService billingService(PaymentService paymentService, BillRepository billRepository) {
        return new BillingServiceImpl(paymentService, billRepository);
    }

    @Bean
    public DiscountService discountService(TripRepository tripRepository) {
        return new DiscountServiceImpl(tripRepository);
    }

    @Bean
    public ReservationValidatorServiceImpl reservationValidatorService() {
        return new ReservationValidatorServiceImpl();
    }


}
