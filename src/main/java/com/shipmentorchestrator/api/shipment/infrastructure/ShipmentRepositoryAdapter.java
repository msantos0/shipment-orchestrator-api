package com.shipmentorchestrator.api.shipment.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentMongoRepository shipmentMongoRepository;

    @Override
    public Shipment save(Shipment shipment) {
        return shipmentMongoRepository.save(shipment);
    }

    @Override
    public List<Shipment> findAll() {
        return shipmentMongoRepository.findAll();
    }

    @Override
    public Optional<Shipment> findById(String id) {
        return shipmentMongoRepository.findById(id);
    }
}
