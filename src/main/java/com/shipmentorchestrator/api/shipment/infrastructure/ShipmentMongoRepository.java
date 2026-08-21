package com.shipmentorchestrator.api.shipment.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShipmentMongoRepository extends MongoRepository<ShipmentDocument, String> {
}
