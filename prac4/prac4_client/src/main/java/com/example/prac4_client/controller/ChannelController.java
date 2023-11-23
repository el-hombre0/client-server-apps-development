package com.example.prac4_client.controller;

import com.example.prac4_client.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class ChannelController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public ChannelController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }


    @PostMapping("/exp")
    public Flux<Order> addOrdersMultiple(@RequestBody List<Order> orderList) {

        Flux<Order> orders = Flux.fromIterable(orderList);
        return rSocketRequester
                .route("orderChannel")
                .data(orders)
                .retrieveFlux(Order.class);
    }

}
