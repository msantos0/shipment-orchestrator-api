package com.shipmentorchestrator.api.shipment.infrastructure;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shipmentorchestrator.api.shipment.domain.TrackingEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tracking_events")
public class TrackingEventDocument {

    @Id
    private String id;
    private String shipmentId;
    private TrackingEventType eventType;
    private Instant eventDate;
    private String location;
    private String description;
}
