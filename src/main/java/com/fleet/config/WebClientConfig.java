package com.fleet.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient vehicleWebClient(@Value("${services.vehicle.url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    WebClient driverWebClient(@Value("${services.driver.url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}