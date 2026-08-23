package com.shipmentorchestrator.api.shipment.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEvent {

    private String id;
    private String shipmentId;
    private TrackingEventType eventType;
    private Instant eventDate;
    private String location;
    private String description;

    public static TrackingEvent create(
            String shipmentId, ShipmentStatus eventType, String location, String description) {
        return TrackingEvent.builder()
                .shipmentId(shipmentId)
                .eventType(TrackingEventType.valueOf(eventType.name()))
                .eventDate(Instant.now())
                .location(location)
                .description(description)
                .build();
    }
}
