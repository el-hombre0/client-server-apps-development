package com.rksp.prac4.controller;

import com.rksp.prac4.model.Order;
import com.rksp.prac4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MainSocketController {
    private final OrderRepository orderRepository;

    @Autowired
    public MainSocketController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @MessageMapping("getOrder")
    public Mono<Order> getOrder(Long id){
        return Mono.justOrEmpty(orderRepository.findOrderById(id));
    }
    @MessageMapping("addOrder")
    public Mono<Order> addOrder(Order order){
        return Mono.justOrEmpty(orderRepository.save(order));
    }

    @MessageMapping("getOrders")
    public Flux<Order> getOrders(){
        return Flux.fromIterable(orderRepository.findAll());
    }

    @MessageMapping("deleteOrder")
    public Mono<Void> deleteOrder(Long id){
        Order order = orderRepository.findOrderById(id);
        orderRepository.delete(order);
        return Mono.empty();
    }

    @MessageMapping("orderChannel")
    public Flux<Order> orderChannel(Flux<Order> orders){
        return orders.flatMap(order -> Mono.fromCallable(() -> orderRepository.save(order)))
                .collectList()
                .flatMapMany(savedOrders -> Flux.fromIterable(savedOrders));
    }
}
