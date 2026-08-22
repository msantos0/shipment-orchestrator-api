package com.shipmentorchestrator.api.shipment.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentMongoRepository shipmentMongoRepository;
    private final ShipmentPersistenceMapper shipmentPersistenceMapper;

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentDocument document = shipmentPersistenceMapper.toDocument(shipment);
        return shipmentPersistenceMapper.toDomain(shipmentMongoRepository.save(document));
    }

    @Override
    public Page<Shipment> findAll(ShipmentStatus status, String trackingCode, Pageable pageable) {
        Page<ShipmentDocument> documents;
        if (status != null && trackingCode != null) {
            documents = shipmentMongoRepository.findByStatusAndTrackingCodeContainingIgnoreCase(
                    status, trackingCode, pageable);
        } else if (status != null) {
            documents = shipmentMongoRepository.findByStatus(status, pageable);
        } else if (trackingCode != null) {
            documents = shipmentMongoRepository.findByTrackingCodeContainingIgnoreCase(trackingCode, pageable);
        } else {
            documents = shipmentMongoRepository.findAll(pageable);
        }
        return documents.map(shipmentPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Shipment> findById(String id) {
        return shipmentMongoRepository.findById(id)
            .map(shipmentPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(String id) {
        shipmentMongoRepository.deleteById(id);
    }
}
