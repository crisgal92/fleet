package com.fleet.client.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleResponse(String id, String plate, String model, Double batteryStatusPercentage) {
}