package com.x_press.rbac_service.service;


import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

public interface RbacService {
    Mono<Map<String, Collection<String>>> getPermissions(String authHeader);
}
