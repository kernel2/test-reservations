package com.transdev.reservations.infrastructure.configuration;

import com.transdev.reservations.domain.ports.incoming.ReservationService;
import com.transdev.reservations.domain.ports.outgoing.PaymentService;
import com.transdev.reservations.domain.services.ReservationServiceImpl;
import com.transdev.reservations.infrastructure.payment.PaymentServiceImpl;
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
}
