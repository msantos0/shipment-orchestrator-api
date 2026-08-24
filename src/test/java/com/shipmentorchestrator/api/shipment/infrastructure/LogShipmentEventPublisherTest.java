package com.shipmentorchestrator.api.shipment.infrastructure;

import static org.mockito.Mockito.verify;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import com.shipmentorchestrator.api.shipment.domain.ShipmentEvent;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

class LogShipmentEventPublisherTest {

    private final Logger logger = org.mockito.Mockito.mock(Logger.class);
    private final LogShipmentEventPublisher publisher = new LogShipmentEventPublisher(logger);

    @Test
    void publishShouldLogShipmentStatusAndId() {
        ShipmentEvent event = new ShipmentEvent("123", ShipmentStatus.DELIVERED, Instant.now());

        publisher.publish(event);

        verify(logger).info(
                "Publishing shipment event: {} for shipment {}",
                ShipmentStatus.DELIVERED,
                "123");
    }
}
