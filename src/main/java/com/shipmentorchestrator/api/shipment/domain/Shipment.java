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
public class Shipment {

    private String id;
    private String origin;
    private String destination;
    private String trackingCode;
    private ShipmentStatus status;
    private Instant createdAt;

    public static Shipment create(String origin, String destination, String trackingCode) {
        return Shipment.builder()
                .origin(origin)
                .destination(destination)
                .trackingCode(trackingCode)
                .status(ShipmentStatus.CREATED)
                .createdAt(Instant.now())
                .build();
    }

    public void update(String origin, String destination, String trackingCode) {
        this.origin = origin;
        this.destination = destination;
        this.trackingCode = trackingCode;
    }

    public void plan() {
        transitionTo(ShipmentStatus.PLANNED);
    }

    public void pickup() {
        transitionTo(ShipmentStatus.PICKED_UP);
    }

    public void startTransit() {
        transitionTo(ShipmentStatus.IN_TRANSIT);
    }

    public void deliver() {
        transitionTo(ShipmentStatus.DELIVERED);
    }

    public void cancel() {
        transitionTo(ShipmentStatus.CANCELLED);
    }

    private void transitionTo(ShipmentStatus targetStatus) {
        if (!isTransitionAllowed(targetStatus)) {
            throw new BusinessException(
                    "Invalid shipment transition from " + status + " to " + targetStatus);
        }
        status = targetStatus;
    }

    private boolean isTransitionAllowed(ShipmentStatus targetStatus) {
        if (status == null) {
            return false;
        }
        return switch (status) {
            case CREATED -> targetStatus == ShipmentStatus.PLANNED;
            case PLANNED -> targetStatus == ShipmentStatus.PICKED_UP
                    || targetStatus == ShipmentStatus.CANCELLED;
            case PICKED_UP -> targetStatus == ShipmentStatus.IN_TRANSIT;
            case IN_TRANSIT -> targetStatus == ShipmentStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
