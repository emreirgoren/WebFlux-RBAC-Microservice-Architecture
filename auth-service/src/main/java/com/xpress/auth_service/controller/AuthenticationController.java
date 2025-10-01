package com.xpress.auth_service.controller;

import com.xpress.auth_service.dto.LoginVM;
import com.xpress.auth_service.dto.RegisterVM;
import com.xpress.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<String> login(@Valid @RequestBody LoginVM loginVM){
        Mono<String> login = authService.login(loginVM);
        return authService.login(loginVM);
    }

    @PostMapping("/register")
    public Mono<String> register(@Valid @RequestBody RegisterVM registerVM) {
        return authService.register(registerVM);
    }

}
