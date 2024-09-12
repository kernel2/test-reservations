package com.transdev.reservations.infrastructure.configuration;

import com.transdev.reservations.domain.ports.incoming.BillingService;
import com.transdev.reservations.domain.ports.incoming.BusService;
import com.transdev.reservations.domain.ports.incoming.ClientService;
import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.BillRepository;
import com.transdev.reservations.domain.ports.outgoing.BusRepository;
import com.transdev.reservations.domain.ports.outgoing.ClientRepository;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import com.transdev.reservations.domain.services.BillingServiceImpl;
import com.transdev.reservations.domain.services.BusServiceImpl;
import com.transdev.reservations.domain.services.ClientServiceImpl;
import com.transdev.reservations.domain.services.ReservationServiceImpl;
import com.transdev.reservations.infrastructure.adapters.payment.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ReservationService reservationService(PaymentService paymentService) {
        return new ReservationServiceImpl(paymentService);
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
}
