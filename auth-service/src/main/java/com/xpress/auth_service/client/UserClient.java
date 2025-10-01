package com.xpress.auth_service.client;

import com.xpress.auth_service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:9002/api/v1/users").build();
    }


    public Mono<UserDto> findByEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/email")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty());
    }

    public Mono<UserDto> createUser(UserDto newUser) {

        return webClient.post()
                .uri("/create")
                .bodyValue(newUser)
                .retrieve()
                .bodyToMono(UserDto.class);

    }
}
