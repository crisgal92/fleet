package com.fleet.client.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DriverResponse(String id, String fullName, String licenseCategory, Double safetyScore,
        String assignmentId) {
}