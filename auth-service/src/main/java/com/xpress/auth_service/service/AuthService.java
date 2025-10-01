package com.xpress.auth_service.service;

import com.xpress.auth_service.dto.LoginVM;
import com.xpress.auth_service.dto.RegisterVM;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<String> login(@Valid LoginVM loginVM);

    Mono<String> register(@Valid RegisterVM registerVM);
}
