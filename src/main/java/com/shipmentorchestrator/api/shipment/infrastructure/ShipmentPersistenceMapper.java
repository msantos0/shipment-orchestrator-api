package com.shipmentorchestrator.api.shipment.infrastructure;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.shipment.domain.Shipment;

@Mapper(componentModel = "spring")
public interface ShipmentPersistenceMapper {

    ShipmentDocument toDocument(Shipment shipment);

    Shipment toDomain(ShipmentDocument document);
}
