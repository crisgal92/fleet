package com.fleet.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleet.dto.FleetInsightDto;
import com.fleet.service.FleetInsightService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/fleet-insights/")
public class FleetInsightController {

    private final FleetInsightService service;

    public FleetInsightController(FleetInsightService service) {
        this.service = service;
    }

    @GetMapping("/{vehicleId}")
    public Mono<FleetInsightDto> getInsight(@PathVariable("vehicleId") String vehicleId) {
        return service.getInsight(vehicleId);
    }
}