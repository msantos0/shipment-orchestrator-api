package com.shipmentorchestrator.api.carrier.infrastructure;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.carrier.domain.Carrier;

@Mapper(componentModel = "spring")
public interface CarrierPersistenceMapper {

    CarrierDocument toDocument(Carrier carrier);

    Carrier toDomain(CarrierDocument document);
}