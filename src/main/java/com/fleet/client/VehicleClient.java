package com.fleet.client;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fleet.client.dto.VehicleResponse;

import reactor.core.publisher.Mono;

@Component
public class VehicleClient {

    private static final String SERVICE = "vehicle-service";

    private final WebClient webClient;

    public VehicleClient(@Qualifier("vehicleWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<VehicleResponse> findById(String vehicleId) {
        WebClient.ResponseSpec spec = webClient.get().uri("/vehicles/{id}", vehicleId).retrieve();
        return RemoteCallSupport.handleErrors(
                RemoteCallSupport.handleStatus(spec, "Vehículo no encontrado: " + vehicleId, SERVICE)
                        .bodyToMono(VehicleResponse.class),
                SERVICE);
    }
}