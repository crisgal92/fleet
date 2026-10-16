package com.fleet.client;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fleet.client.dto.DriverResponse;

import reactor.core.publisher.Mono;

@Component
public class DriverClient {

    private static final String SERVICE = "driver-service";

    private final WebClient webClient;

    public DriverClient(@Qualifier("driverWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<DriverResponse> findByVehicleId(String vehicleId) {
        WebClient.ResponseSpec spec = webClient.get().uri("/drivers/by-vehicle/{id}", vehicleId).retrieve();
        return RemoteCallSupport.handleErrors(
                RemoteCallSupport.handleStatus(spec, "No hay conductor asignado al vehículo: " + vehicleId,
                        SERVICE).bodyToMono(DriverResponse.class),
                SERVICE);
    }
}