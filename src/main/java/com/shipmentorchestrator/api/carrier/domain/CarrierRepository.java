package com.shipmentorchestrator.api.carrier.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarrierRepository {

    Carrier save(Carrier carrier);

    Optional<Carrier> findById(String id);

    Page<Carrier> findAll(String name, Boolean active, Pageable pageable);
}