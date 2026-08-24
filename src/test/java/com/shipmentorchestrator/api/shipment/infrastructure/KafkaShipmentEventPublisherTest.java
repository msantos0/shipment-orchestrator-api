package com.shipmentorchestrator.api.shipment.infrastructure;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

import com.shipmentorchestrator.api.shipment.domain.ShipmentEvent;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

class KafkaShipmentEventPublisherTest {

    @SuppressWarnings("unchecked")
    private final KafkaTemplate<String, ShipmentKafkaEvent> kafkaTemplate =
            org.mockito.Mockito.mock(KafkaTemplate.class);
    private final Logger logger = org.mockito.Mockito.mock(Logger.class);
    private final KafkaShipmentEventPublisher publisher = new KafkaShipmentEventPublisher(
            kafkaTemplate, "shipment-events", logger);

    @Test
    void publishShouldSendShipmentEventWithShipmentIdKey() {
        ShipmentEvent event = new ShipmentEvent("shipment-1", ShipmentStatus.CREATED, Instant.now());
        when(kafkaTemplate.send(eq("shipment-events"), eq("shipment-1"), org.mockito.ArgumentMatchers.any()))
                .thenReturn(CompletableFuture.completedFuture(null));

        publisher.publish(event);

        verify(kafkaTemplate).send(
                eq("shipment-events"),
                eq("shipment-1"),
                org.mockito.ArgumentMatchers.argThat(message ->
                        message.shipmentId().equals("shipment-1")
                                && message.eventType().equals("CREATED")
                                && message.occurredAt().equals(event.occurredAt())));
    }

    @Test
    void publishShouldNotPropagateKafkaFailure() {
        ShipmentEvent event = new ShipmentEvent("shipment-1", ShipmentStatus.DELIVERED, Instant.now());
        RuntimeException failure = new RuntimeException("broker unavailable");
        when(kafkaTemplate.send(eq("shipment-events"), eq("shipment-1"), org.mockito.ArgumentMatchers.any()))
                .thenReturn(CompletableFuture.failedFuture(failure));

        assertThatCode(() -> publisher.publish(event)).doesNotThrowAnyException();

        verify(logger).error(
                eq("Failed to publish shipment event {} for shipment {}"),
                eq(ShipmentStatus.DELIVERED),
                eq("shipment-1"),
                eq(failure));
    }
}
