package com.x_press.rbac_service.repository;

import com.x_press.rbac_service.domain.Rbac;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface RbacRepository extends R2dbcRepository<Rbac, Long> {
    Flux<Rbac> findByEmail(String email);
}
