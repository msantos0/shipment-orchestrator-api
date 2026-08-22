package com.shipmentorchestrator.api.shipment.infrastructure;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shipments")
public class ShipmentDocument {

    @Id
    private String id;
    private String origin;
    private String destination;
    private String trackingCode;
    private ShipmentStatus status;
    private Instant createdAt;
}
