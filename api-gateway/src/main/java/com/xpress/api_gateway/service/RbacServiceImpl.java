package com.xpress.api_gateway.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class RbacServiceImpl implements RbacService {

    private final WebClient webClient;

    public RbacServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9003/api/v1") // Permission Service
                .build();
    }

    @Override
    public Mono<Map<String, List<String>>> getPermissions(String jwtToken) {
        return webClient.post()
                .uri("/rbac/check")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<String>>>() {});
    }
}
