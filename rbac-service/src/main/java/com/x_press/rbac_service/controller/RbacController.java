package com.x_press.rbac_service.controller;

import com.x_press.rbac_service.service.RbacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rbac")
public class RbacController {

    private final RbacService rbacService;

    public RbacController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @PostMapping("/check")
    public Mono<ResponseEntity<Map<String, Collection<String>>>> checkPermissions(@RequestHeader("Authorization") String authHeader) {

        Mono<Map<String, Collection<String>>> permissions = rbacService.getPermissions(authHeader);
        rbacService.getPermissions(authHeader)
                .subscribe(permis -> {
                    System.out.println("Permissions: " + permis);
                });
        return rbacService.getPermissions(authHeader).map(ResponseEntity::ok);
    }


}
