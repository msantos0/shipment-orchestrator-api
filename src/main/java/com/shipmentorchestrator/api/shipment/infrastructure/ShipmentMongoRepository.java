package com.shipmentorchestrator.api.shipment.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shipmentorchestrator.api.shipment.domain.Shipment;

public interface ShipmentMongoRepository extends MongoRepository<Shipment, String> {
}
