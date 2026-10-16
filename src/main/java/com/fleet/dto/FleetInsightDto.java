package com.fleet.dto;


import java.time.Instant;

public record FleetInsightDto(
        String assignmentId,
        VehicleInfo vehicle,
        DriverInfo driver,
        Instant compositeTimestamp) {
}