package com.xpress.user_service.repository;


import com.xpress.user_service.domain.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


public interface UserRepository extends R2dbcRepository<User, Long> {


    Mono<User> findByEmail(String email);
}
