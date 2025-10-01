package com.xpress.api_gateway.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService{

    private final WebClient webClient;

    public PermissionServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://permission-service:8081") // Permission Service
                .build();
    }

    @Override
    public Mono<Map<String, List<String>>> getPermissions(String jwtToken) {
        return webClient.post()
                .uri("/permissions/check")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<String>>>() {});
    }
}
