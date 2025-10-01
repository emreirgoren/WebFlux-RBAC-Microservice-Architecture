package com.x_press.order_service.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @GetMapping
    public Mono<String> getOrder(){

        return Mono.just("order");
    }

    @PostMapping
    public Mono<String> createOrder(){

        return Mono.just("create order");
    }

    @PutMapping
    public Mono<String> updateOrder(){

        return Mono.just("update order");
    }

}
