package com.xpress.api_gateway.utils;

import com.xpress.api_gateway.domain.Operation;
import com.xpress.api_gateway.service.PermissionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpMethod;

@Component
public class JwtRequestFilter implements WebFilter {

    private final Jwtutil jwtutil;

    private final PermissionService permissionService;

    public JwtRequestFilter(Jwtutil jwtutil, PermissionService permissionService) {
        this.jwtutil = jwtutil;
        this.permissionService = permissionService;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if(path.contains("/api/v1/auth")){
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Missing or invalid Authorization header"));
        }

        String jwtToken = authHeader.substring(7);


        if(!jwtutil.validateToken(jwtToken)){
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid or expired token"));
        }

        HttpMethod httpMethod = exchange.getRequest().getMethod();
        Operation operation = Operation.fromHttpMethod(httpMethod);

        return permissionService.getPermissions(jwtToken)
                .flatMap(permissions ->{
                    boolean allowed = permissions.entrySet().stream()
                            .anyMatch( entry -> path.startsWith(entry.getKey()) &&
                                    entry.getValue().contains(operation.name()));
                    if(allowed){
                        var authorities = permissions.entrySet().stream()
                                .flatMap(entry -> entry.getValue().stream()
                                        .map(op -> new SimpleGrantedAuthority(entry.getKey() + ":" + op)))
                                .toList();

                        Authentication auth = new UsernamePasswordAuthenticationToken(jwtToken, null, authorities);

                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                                .doOnError(err -> System.out.println("Error in chain: " + err));
                    }
                    else {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access"));
                    }
                });

    }

}
