package rtu.rksp.electrocars.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rtu.rksp.electrocars.exception.CustomException;
import rtu.rksp.electrocars.model.Order;
import rtu.rksp.electrocars.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Получение заказа по ID
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Order>> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Получение всех заказов
     * @param distanceToClient
     * @return заказы
     */
    @GetMapping
    public Flux<Order> getAllOrders(
            @RequestParam(name = "distance_to_client", required = false) Integer distanceToClient) {
        Flux<Order> orders = orderRepository.findAll();
        /**
         * Если указана дистанция до клиента, то возвращаются заказы с дистанцией меньше заданной
         */
        if (distanceToClient != null && distanceToClient > 0) {
            orders = orders.filter(order -> order.getDistanceToClient() <= distanceToClient);
        }

        return orders
                // .map(this::transformOrder) // Применение оператора map для преобразования объектов Order
                .onErrorResume(e -> {
                    // Обработка ошибок
                    return Flux.error(new CustomException("Failed to fetch orders", e));
                })
                .onBackpressureBuffer(); // Работа в формате backpressure
    }

    /**
     * Создание заказа
     * @param order
     * @return сохранение заказа в репозитории
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    /**
     * Изменение заказа
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Order>> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        return orderRepository.findById(id)
                .flatMap(existingOrder -> {
                    existingOrder.setClientName(updatedOrder.getClientName());
                    existingOrder.setClientPhone(updatedOrder.getClientPhone());
                    existingOrder.setCarModel(updatedOrder.getCarModel());
                    existingOrder.setRequiredKiloWatts(updatedOrder.getRequiredKiloWatts());
                    existingOrder.setDistanceToClient(updatedOrder.getDistanceToClient());
                    existingOrder.setCost(updatedOrder.getCost());
                    return orderRepository.save(existingOrder);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Удаление заказа
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .flatMap(existingOrder -> orderRepository.delete(existingOrder)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Изменение имени заказа на верхний регистр
     * @param order
     * @return
     */
    // private Order transformOrder(Order order) {
    //     // Пример преобразования объекта Order
    //     order.setClientName(order.getClientName().toUpperCase());
    //     return order;
    // }
}
