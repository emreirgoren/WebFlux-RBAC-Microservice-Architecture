package com.x_press.rbac_service.service;

import com.x_press.rbac_service.domain.Rbac;
import com.x_press.rbac_service.repository.RbacRepository;
import com.x_press.rbac_service.utils.JwtUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

@Service
public class RbacServiceImpl implements RbacService {

    private final JwtUtils jwtUtils;
    private final RbacRepository rbacRepository;

    public RbacServiceImpl(JwtUtils jwtUtils, RbacRepository rbacRepository) {
        this.jwtUtils = jwtUtils;
        this.rbacRepository = rbacRepository;
    }

    @Override
    public Mono<Map<String, Collection<String>>> getPermissions(String authHeader) {

        String token = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }

        var email = jwtUtils.extractEmail(token);

        return rbacRepository.findByEmail(email).collectMultimap(Rbac::getPath, Rbac::getMethod);
    }
}
