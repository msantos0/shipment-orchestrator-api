package com.shipmentorchestrator.api.carrier.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Test
    void findByIdShouldReturnCarrier() throws Exception {
        when(carrierService.findById("carrier-1")).thenReturn(new CarrierOutput(
                "carrier-1", "Carrier", "12345678000199", true, Instant.parse("2026-01-01T00:00:00Z")));

        mockMvc.perform(get("/api/v1/carriers/carrier-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("carrier-1"))
                .andExpect(jsonPath("$.name").value("Carrier"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void findByIdShouldReturnNotFoundWhenCarrierDoesNotExist() throws Exception {
        when(carrierService.findById("missing"))
                .thenThrow(new com.shipmentorchestrator.api.carrier.application.CarrierNotFoundException("missing"));

        mockMvc.perform(get("/api/v1/carriers/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Carrier not found: missing"));
    }

    @Test
    void findAllShouldReturnPaginatedCarriers() throws Exception {
        CarrierOutput output = new CarrierOutput(
                "carrier-1", "Carrier", "12345678000199", true, Instant.parse("2026-01-01T00:00:00Z"));
        when(carrierService.findAll(org.mockito.ArgumentMatchers.isNull(), org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(output), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/api/v1/carriers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("carrier-1"))
                .andExpect(jsonPath("$.content[0].name").value("Carrier"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(carrierService).findAll(null, null, PageRequest.of(0, 20,
                org.springframework.data.domain.Sort.by("createdAt").descending()));
    }

    @Test
    void findAllShouldAcceptFiltersAndPagination() throws Exception {
        when(carrierService.findAll(org.mockito.ArgumentMatchers.eq("Express"),
                org.mockito.ArgumentMatchers.eq(false), org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(1, 5), 0));

        mockMvc.perform(get("/api/v1/carriers")
                .param("name", "Express")
                .param("active", "false")
                .param("page", "1")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(5));

        verify(carrierService).findAll(org.mockito.ArgumentMatchers.eq("Express"),
                org.mockito.ArgumentMatchers.eq(false), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void updateShouldReturnUpdatedCarrier() throws Exception {
        CarrierOutput output = new CarrierOutput(
                "carrier-1", "Updated Carrier", "12345678000199", false,
                Instant.parse("2026-01-01T00:00:00Z"));
        when(carrierService.update(org.mockito.ArgumentMatchers.eq("carrier-1"), any()))
                .thenReturn(output);

        mockMvc.perform(put("/api/v1/carriers/carrier-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Carrier\",\"active\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("carrier-1"))
                .andExpect(jsonPath("$.name").value("Updated Carrier"))
                .andExpect(jsonPath("$.active").value(false));

        verify(carrierService).update(org.mockito.ArgumentMatchers.eq("carrier-1"), any());
    }

    @Test
    void updateShouldReturnNotFoundWhenCarrierDoesNotExist() throws Exception {
        when(carrierService.update(org.mockito.ArgumentMatchers.eq("missing"), any()))
                .thenThrow(new com.shipmentorchestrator.api.carrier.application.CarrierNotFoundException("missing"));

        mockMvc.perform(put("/api/v1/carriers/missing")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Carrier\",\"active\":true}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Carrier not found: missing"));
    }

        @Test
        void deleteShouldReturnNoContent() throws Exception {
                mockMvc.perform(delete("/api/v1/carriers/carrier-1"))
                                .andExpect(status().isNoContent());

                verify(carrierService).delete("carrier-1");
        }

        @Test
        void deleteShouldReturnNotFoundWhenCarrierDoesNotExist() throws Exception {
                org.mockito.Mockito.doThrow(
                                new com.shipmentorchestrator.api.carrier.application.CarrierNotFoundException("missing"))
                                .when(carrierService).delete("missing");

                mockMvc.perform(delete("/api/v1/carriers/missing"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.error").value("Carrier not found: missing"));
        }
}