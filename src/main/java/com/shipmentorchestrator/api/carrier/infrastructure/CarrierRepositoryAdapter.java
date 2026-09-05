package com.shipmentorchestrator.api.carrier.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Optional<Carrier> findById(String id) {
        return carrierMongoRepository.findById(id)
                .map(carrierPersistenceMapper::toDomain);
    }

    @Override
    public Page<Carrier> findAll(String name, Boolean active, Pageable pageable) {
        Page<CarrierDocument> documents;
        if (name != null && active != null) {
            documents = carrierMongoRepository.findByNameContainingIgnoreCaseAndActive(name, active, pageable);
        } else if (name != null) {
            documents = carrierMongoRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (active != null) {
            documents = carrierMongoRepository.findByActive(active, pageable);
        } else {
            documents = carrierMongoRepository.findAll(pageable);
        }
        return documents.map(carrierPersistenceMapper::toDomain);
    }
}