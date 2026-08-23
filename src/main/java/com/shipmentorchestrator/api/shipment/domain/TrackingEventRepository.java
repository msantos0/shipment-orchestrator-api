package com.shipmentorchestrator.api.shipment.domain;

import java.util.List;

public interface TrackingEventRepository {

    TrackingEvent save(TrackingEvent trackingEvent);

    List<TrackingEvent> findByShipmentId(String shipmentId);
}
