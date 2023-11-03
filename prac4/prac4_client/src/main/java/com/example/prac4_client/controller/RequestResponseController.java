package com.example.prac4_client.controller;

import com.example.prac4_client.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class RequestResponseController {
    private final RSocketRequester rSocketRequester;

    @Autowired
    public RequestResponseController(RSocketRequester rSocketRequester){
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrder(@PathVariable Long id){
        return rSocketRequester
                .route("getOrder")
                .data(id)
                .retrieveMono(Order.class);
    }

    @PostMapping
    public Mono<Order> addOrder(@RequestBody Order order){
        return rSocketRequester
                .route("addOrder")
                .data(order)
                .retrieveMono(Order.class);
    }
}
