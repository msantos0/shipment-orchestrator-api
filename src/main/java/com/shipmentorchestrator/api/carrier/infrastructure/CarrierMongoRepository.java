package com.shipmentorchestrator.api.carrier.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarrierMongoRepository extends MongoRepository<CarrierDocument, String> {

	Page<CarrierDocument> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<CarrierDocument> findByActive(boolean active, Pageable pageable);

	Page<CarrierDocument> findByNameContainingIgnoreCaseAndActive(
			String name, boolean active, Pageable pageable);
}