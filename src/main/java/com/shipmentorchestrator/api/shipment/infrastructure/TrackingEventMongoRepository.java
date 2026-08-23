package com.shipmentorchestrator.api.shipment.infrastructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackingEventMongoRepository extends MongoRepository<TrackingEventDocument, String> {

    List<TrackingEventDocument> findByShipmentIdOrderByEventDateAsc(String shipmentId);
}
