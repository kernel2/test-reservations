package com.transdev.reservations.infrastructure.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transdev.reservations.application.dto.BillDTO;
import com.transdev.reservations.application.dto.ReservationDTO;
import com.transdev.reservations.application.dto.TripDTO;
import com.transdev.reservations.application.services.ReservationApplicationService;
import com.transdev.reservations.domain.model.Bill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    private static ReservationDTO mockReservation;

    @MockBean
    private ReservationApplicationService reservationApplicationService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        mockReservation = new ReservationDTO();
        mockReservation.setClientId(1L);

        List<TripDTO> trips = new ArrayList<>();
        TripDTO trip1 = new TripDTO();
        trip1.setId(1L);
        trip1.setBusNumber("BUS123");
        trip1.setDateOfTravel(LocalDateTime.of(2024, 10, 1, 10, 0));
        trip1.setSeatsPerTrip(45);
        trip1.setPrice(new BigDecimal("60.00"));

        trips.add(trip1);
        mockReservation.setTrips(trips);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void testCreateReservation() throws Exception {
        when(reservationApplicationService.createReservation(any(ReservationDTO.class)))
                .thenReturn(mockReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReservation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.trips[0].busNumber").value("BUS123"));
    }

    @Test
    @Order(2)
    void testGetReservation() throws Exception {
        when(reservationApplicationService.getReservation(anyLong()))
                .thenReturn(mockReservation);

        mockMvc.perform(get("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.trips[0].busNumber").value("BUS123"));
    }

    @Test
    @Order(3)
    void testDeleteReservation() throws Exception {
        doNothing().when(reservationApplicationService).deleteReservation(anyLong());

        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    void testUpdateReservation() throws Exception {
        when(reservationApplicationService.updateReservation(anyLong(), any(ReservationDTO.class)))
                .thenReturn(mockReservation);

        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.trips[0].busNumber").value("BUS123"));
    }

    @Test
    @Order(5)
    void testPayReservation() throws Exception {
        BillDTO billDTO = new BillDTO(1L, "CREDIT_CARD");

        when(reservationApplicationService.payReservation(anyLong(), anyString()))
                .thenReturn(new Bill(1L, "CREDIT_CARD"));

        mockMvc.perform(post("/api/reservations/1/pay/CREDIT_CARD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentType").value("CREDIT_CARD"));
    }
}
