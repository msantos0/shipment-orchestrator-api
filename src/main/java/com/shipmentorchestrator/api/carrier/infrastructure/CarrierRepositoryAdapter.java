package com.shipmentorchestrator.api.carrier.infrastructure;

import org.springframework.stereotype.Repository;

import com.shipmentorchestrator.api.carrier.domain.Carrier;
import com.shipmentorchestrator.api.carrier.domain.CarrierRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarrierRepositoryAdapter implements CarrierRepository {

    private final CarrierMongoRepository carrierMongoRepository;
    private final CarrierPersistenceMapper carrierPersistenceMapper;

    @Override
    public Carrier save(Carrier carrier) {
        CarrierDocument document = carrierPersistenceMapper.toDocument(carrier);
        return carrierPersistenceMapper.toDomain(carrierMongoRepository.save(document));
    }
}