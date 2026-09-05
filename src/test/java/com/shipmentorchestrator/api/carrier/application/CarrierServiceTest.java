package com.shipmentorchestrator.api.carrier.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Test
    void findAllShouldMapFilteredCarriers() {
        Carrier carrier = carrier("carrier-1", "Carrier", true);
        CarrierOutput output = output(carrier);
        PageRequest pageable = PageRequest.of(0, 20);
        when(carrierRepository.findAll("Carrier", true, pageable))
                .thenReturn(new PageImpl<>(java.util.List.of(carrier), pageable, 1));
        when(carrierMapper.toOutput(carrier)).thenReturn(output);

        var result = carrierService.findAll("Carrier", true, pageable);

        assertThat(result.getContent()).containsExactly(output);
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(carrierRepository).findAll("Carrier", true, pageable);
    }

    @Test
    void updateShouldSaveNameAndActiveStatus() {
        Carrier carrier = carrier("carrier-1", "Carrier", true);
        CarrierOutput output = output(carrier);
        when(carrierRepository.findById("carrier-1")).thenReturn(Optional.of(carrier));
        when(carrierRepository.save(carrier)).thenReturn(carrier);
        when(carrierMapper.toOutput(carrier)).thenReturn(output);

        CarrierOutput result = carrierService.update(
                "carrier-1", new UpdateCarrierCommand("Updated Carrier", false));

        assertThat(result).isEqualTo(output);
        assertThat(carrier.getName()).isEqualTo("Updated Carrier");
        assertThat(carrier.isActive()).isFalse();
        verify(carrierRepository).save(carrier);
    }

    @Test
    void updateShouldThrowWhenCarrierDoesNotExist() {
        when(carrierRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carrierService.update(
                "missing", new UpdateCarrierCommand("Carrier", true)))
                .isInstanceOf(CarrierNotFoundException.class)
                .hasMessage("Carrier not found: missing");

        verify(carrierRepository, never()).save(any(Carrier.class));
    }

    @Test
    void deleteShouldDeactivateAndSaveCarrier() {
        Carrier carrier = carrier("carrier-1", "Carrier", true);
        when(carrierRepository.findById("carrier-1")).thenReturn(Optional.of(carrier));
        when(carrierRepository.save(carrier)).thenReturn(carrier);

        carrierService.delete("carrier-1");

        assertThat(carrier.isActive()).isFalse();
        verify(carrierRepository).save(carrier);
    }

    @Test
    void deleteShouldThrowWhenCarrierDoesNotExist() {
        when(carrierRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carrierService.delete("missing"))
                .isInstanceOf(CarrierNotFoundException.class)
                .hasMessage("Carrier not found: missing");

        verify(carrierRepository, never()).save(any(Carrier.class));
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