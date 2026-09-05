package com.shipmentorchestrator.api.carrier.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.shipmentorchestrator.api.carrier.application.CarrierOutput;
import com.shipmentorchestrator.api.carrier.application.CarrierService;

@WebMvcTest(CarrierController.class)
class CarrierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrierService carrierService;

    @Test
    void createShouldReturnCreatedCarrier() throws Exception {
        when(carrierService.create(any())).thenReturn(new CarrierOutput(
                "carrier-1", "Carrier", "12345678000199", true, Instant.parse("2026-01-01T00:00:00Z")));

        mockMvc.perform(post("/api/v1/carriers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Carrier","cnpj":"12345678000199"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("carrier-1"))
                .andExpect(jsonPath("$.name").value("Carrier"))
                .andExpect(jsonPath("$.cnpj").value("12345678000199"))
                .andExpect(jsonPath("$.active").value(true));

        verify(carrierService).create(any());
    }

    @Test
    void createShouldRejectBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/carriers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"cnpj\":\"12345678000199\"}"))
                .andExpect(status().isBadRequest());
    }
}