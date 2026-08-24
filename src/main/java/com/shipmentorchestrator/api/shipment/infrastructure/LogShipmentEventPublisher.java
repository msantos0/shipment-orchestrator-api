package com.shipmentorchestrator.api.shipment.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.shipmentorchestrator.api.shipment.domain.ShipmentEvent;
import com.shipmentorchestrator.api.shipment.domain.ShipmentEventPublisher;

@Component
public class LogShipmentEventPublisher implements ShipmentEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogShipmentEventPublisher.class);

    private final Logger logger;

    public LogShipmentEventPublisher() {
        this(LOGGER);
    }

    LogShipmentEventPublisher(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void publish(ShipmentEvent event) {
        logger.info("Publishing shipment event: {} for shipment {}", event.status(), event.shipmentId());
    }
}
