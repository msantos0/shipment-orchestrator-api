package com.shipmentorchestrator.api.carrier.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarrierMongoRepository extends MongoRepository<CarrierDocument, String> {
}