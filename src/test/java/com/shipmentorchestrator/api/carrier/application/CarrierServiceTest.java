package com.shipmentorchestrator.api.carrier.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shipmentorchestrator.api.carrier.domain.Carrier;
import com.shipmentorchestrator.api.carrier.domain.CarrierRepository;

@ExtendWith(MockitoExtension.class)
class CarrierServiceTest {

    @Mock
    private CarrierRepository carrierRepository;

    @Mock
    private CarrierMapper carrierMapper;

    @InjectMocks
    private CarrierService carrierService;

    @Test
    void createShouldSaveActiveCarrier() {
        Carrier savedCarrier = carrier("carrier-1", "Carrier", true);
        CarrierOutput output = output(savedCarrier);
        when(carrierRepository.save(any(Carrier.class))).thenReturn(savedCarrier);
        when(carrierMapper.toOutput(savedCarrier)).thenReturn(output);

        CarrierOutput result = carrierService.create(
                new CreateCarrierCommand("Carrier", "12345678000199"));

        assertThat(result).isEqualTo(output);
        verify(carrierRepository).save(org.mockito.ArgumentMatchers.argThat(carrier ->
            carrier.getName().equals("Carrier")
                && carrier.getCnpj().equals("12345678000199")
                && carrier.isActive()));
    }

    private Carrier carrier(String id, String name, boolean active) {
        return Carrier.builder()
                .id(id)
                .name(name)
                .cnpj("12345678000199")
                .active(active)
                .createdAt(Instant.now())
                .build();
    }

    private CarrierOutput output(Carrier carrier) {
        return new CarrierOutput(
                carrier.getId(), carrier.getName(), carrier.getCnpj(), carrier.isActive(), carrier.getCreatedAt());
    }
}