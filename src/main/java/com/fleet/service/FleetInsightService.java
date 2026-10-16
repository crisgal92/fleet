package com.fleet.service;



import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.fleet.client.DriverClient;
import com.fleet.client.VehicleClient;
import com.fleet.client.dto.DriverResponse;
import com.fleet.client.dto.VehicleResponse;
import com.fleet.dto.DriverInfo;
import com.fleet.dto.FleetInsightDto;
import com.fleet.dto.VehicleInfo;

import reactor.core.publisher.Mono;

@Service
public class FleetInsightService {

    private final VehicleClient vehicleClient;
    private final DriverClient driverClient;

    public FleetInsightService(VehicleClient vehicleClient, DriverClient driverClient) {
        this.vehicleClient = vehicleClient;
        this.driverClient = driverClient;
    }

    // Mono.zip se suscribe a las dos llamadas al mismo tiempo: van en paralelo.
    // Si una falla, se cancela la otra y el error se propaga.
    public Mono<FleetInsightDto> getInsight(String vehicleId) {
        return Mono.zip(vehicleClient.findById(vehicleId), driverClient.findByVehicleId(vehicleId))
                .map(tuple -> merge(tuple.getT1(), tuple.getT2()));
    }

    private FleetInsightDto merge(VehicleResponse vehicle, DriverResponse driver) {
        return new FleetInsightDto(
                driver.assignmentId(),
                new VehicleInfo(vehicle.id(), vehicle.plate(), vehicle.model(), vehicle.batteryStatusPercentage()),
                new DriverInfo(driver.id(), driver.fullName(), driver.licenseCategory(), driver.safetyScore()),
                Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }
}