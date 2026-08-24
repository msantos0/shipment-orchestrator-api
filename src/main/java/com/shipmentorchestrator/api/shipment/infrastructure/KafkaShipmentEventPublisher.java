package com.shipmentorchestrator.api.shipment.infrastructure;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shipmentorchestrator.api.shipment.domain.ShipmentEvent;
import com.shipmentorchestrator.api.shipment.domain.ShipmentEventPublisher;

@Component
@ConditionalOnProperty(
        prefix = "shipment.kafka",
        name = "publisher",
        havingValue = "kafka")        
public class KafkaShipmentEventPublisher implements ShipmentEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaShipmentEventPublisher.class);

    private final KafkaTemplate<String, ShipmentKafkaEvent> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaShipmentEventPublisher(
            KafkaTemplate<String, ShipmentKafkaEvent> kafkaTemplate,
            org.springframework.core.env.Environment environment) {
        this(kafkaTemplate, environment.getRequiredProperty("shipment.kafka.topic"), LOGGER);
    }

    KafkaShipmentEventPublisher(
            KafkaTemplate<String, ShipmentKafkaEvent> kafkaTemplate,
            String topic,
            Logger logger) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.logger = logger;
    }

    private final Logger logger;

    @Override
    public void publish(ShipmentEvent event) {
        ShipmentKafkaEvent message = new ShipmentKafkaEvent(
                event.shipmentId(),
                event.status().name(),
                event.occurredAt());

        try {
            kafkaTemplate.send(topic, event.shipmentId(), message)
                    .whenComplete((result, error) -> logResult(event, result, error));
        } catch (RuntimeException error) {
            logFailure(event, error);
        }
    }

    private void logResult(
            ShipmentEvent event,
            SendResult<String, ShipmentKafkaEvent> result,
            Throwable error) {
        if (error != null) {
            logFailure(event, error);
            return;
        }

        RecordMetadata metadata = result.getRecordMetadata();
        logger.debug(
                "Published shipment event for shipment {} to {}-{}-{}",
                event.shipmentId(),
                metadata.topic(),
                metadata.partition(),
                metadata.offset());
    }

    private void logFailure(ShipmentEvent event, Throwable error) {
        logger.error(
                "Failed to publish shipment event {} for shipment {}",
                event.status(),
                event.shipmentId(),
                error);
    }
}
