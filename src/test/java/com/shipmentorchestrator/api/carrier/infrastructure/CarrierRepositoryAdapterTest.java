package com.shipmentorchestrator.api.carrier.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shipmentorchestrator.api.carrier.domain.Carrier;

@ExtendWith(MockitoExtension.class)
class CarrierRepositoryAdapterTest {

    @Mock
    private CarrierMongoRepository carrierMongoRepository;

    @Mock
    private CarrierPersistenceMapper carrierPersistenceMapper;

    @InjectMocks
    private CarrierRepositoryAdapter carrierRepositoryAdapter;

    @Test
    void saveShouldMapAndPersistCarrier() {
        Carrier carrier = Carrier.create("Carrier", "12345678000199");
        CarrierDocument document = CarrierDocument.builder().name("Carrier").cnpj("12345678000199").build();
        Carrier savedCarrier = Carrier.builder().id("carrier-1").name("Carrier")
                .cnpj("12345678000199").active(true).createdAt(carrier.getCreatedAt()).build();
        CarrierDocument savedDocument = CarrierDocument.builder().id("carrier-1").name("Carrier")
                .cnpj("12345678000199").active(true).createdAt(carrier.getCreatedAt()).build();

        when(carrierPersistenceMapper.toDocument(carrier)).thenReturn(document);
        when(carrierMongoRepository.save(document)).thenReturn(savedDocument);
        when(carrierPersistenceMapper.toDomain(savedDocument)).thenReturn(savedCarrier);

        Carrier result = carrierRepositoryAdapter.save(carrier);

        assertThat(result).isSameAs(savedCarrier);
        verify(carrierPersistenceMapper).toDocument(carrier);
        verify(carrierMongoRepository).save(document);
        verify(carrierPersistenceMapper).toDomain(savedDocument);
    }

    @Test
    void findAllShouldUseNameAndActiveFilters() {
        CarrierDocument document = CarrierDocument.builder().id("carrier-1").name("Carrier")
                .active(true).build();
        Carrier carrier = Carrier.builder().id("carrier-1").name("Carrier").active(true).build();
        PageRequest pageable = PageRequest.of(0, 20);
        when(carrierMongoRepository.findByNameContainingIgnoreCaseAndActive("Carrier", true, pageable))
                .thenReturn(new PageImpl<>(java.util.List.of(document), pageable, 1));
        when(carrierPersistenceMapper.toDomain(document)).thenReturn(carrier);

        var result = carrierRepositoryAdapter.findAll("Carrier", true, pageable);

        assertThat(result.getContent()).containsExactly(carrier);
        verify(carrierMongoRepository).findByNameContainingIgnoreCaseAndActive("Carrier", true, pageable);
    }

        @Test
        void findByIdShouldMapCarrier() {
                CarrierDocument document = CarrierDocument.builder().id("carrier-1").name("Carrier").build();
                Carrier carrier = Carrier.builder().id("carrier-1").name("Carrier").build();
                when(carrierMongoRepository.findById("carrier-1")).thenReturn(Optional.of(document));
                when(carrierPersistenceMapper.toDomain(document)).thenReturn(carrier);

                assertThat(carrierRepositoryAdapter.findById("carrier-1")).containsSame(carrier);
        }
}