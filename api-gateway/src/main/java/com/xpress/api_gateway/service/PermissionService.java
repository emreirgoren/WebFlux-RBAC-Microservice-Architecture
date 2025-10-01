package com.xpress.api_gateway.service;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public interface PermissionService {


    Mono<Map<String, List<String>>> getPermissions(String jwtToken);
}
