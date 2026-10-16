package com.fleet.client;


import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

final class RemoteCallSupport {

    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    private RemoteCallSupport() {
    }

    static WebClient.ResponseSpec handleStatus(WebClient.ResponseSpec spec, String notFoundMessage,
            String service) {
        return spec
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundMessage)))
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                                service + " respondió con error")));
    }

    static <T> Mono<T> handleErrors(Mono<T> mono, String service) {
        return mono
                .timeout(TIMEOUT)
                .onErrorMap(TimeoutException.class,
                        e -> new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, service + " tardó demasiado"))
                .onErrorMap(WebClientRequestException.class,
                        e -> new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                                service + " no está disponible"));
    }
}