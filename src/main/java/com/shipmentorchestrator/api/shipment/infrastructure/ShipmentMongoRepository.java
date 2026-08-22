package com.shipmentorchestrator.api.shipment.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

public interface ShipmentMongoRepository extends MongoRepository<ShipmentDocument, String> {

	Page<ShipmentDocument> findByStatus(ShipmentStatus status, Pageable pageable);

	Page<ShipmentDocument> findByTrackingCodeContainingIgnoreCase(String trackingCode, Pageable pageable);

	Page<ShipmentDocument> findByStatusAndTrackingCodeContainingIgnoreCase(
			ShipmentStatus status, String trackingCode, Pageable pageable);
}
