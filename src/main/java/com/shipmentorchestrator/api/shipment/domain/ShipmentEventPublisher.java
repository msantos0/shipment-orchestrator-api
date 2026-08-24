package com.shipmentorchestrator.api.shipment.domain;

public interface ShipmentEventPublisher {

    void publish(ShipmentEvent event);
}
