package rtu.rksp.electrocars;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rtu.rksp.electrocars.controller.OrderController;
import rtu.rksp.electrocars.model.Order;
import rtu.rksp.electrocars.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Test
    public void testGetCatById() {
        // Создание фиктивного заказа
        Order order = new Order();
        order.setId(1L);
        order.setClientName("Ivan");
        order.setClientPhone("79993452312");
        order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(30);
        order.setDistanceToClient(23);

        // Создание мок репозитория
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        when(orderRepository.findById(1L)).thenReturn(Mono.just(order));

        // Создание экземпляра контроллера
        OrderController orderController = new OrderController(orderRepository);

        // Вызов метода контроллера и проверка результат
        ResponseEntity<Order> response = orderController.getOrderById(1L).block();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetAllOrders() {
        // Создание списка фиктивных заказов
        Order order1 = new Order();
        order1.setId(1L);
        order1.setClientName("Ivan");
        order1.setClientPhone("79993452312");
        order1.setCarModel("Tesla Model S");
        order1.setRequiredKiloWatts(10);
        order1.setDistanceToClient(23);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setClientName("Boris");
        order2.setClientPhone("79665327562");
        order2.setCarModel("Nissan Leaf");
        order2.setRequiredKiloWatts(12);
        order2.setDistanceToClient(17);

        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        when(orderRepository.findAll()).thenReturn(Flux.just(order1, order2));

        OrderController orderController = new OrderController(orderRepository);

        Flux<Order> response = orderController.getAllOrders(null);
        assertEquals(2, response.collectList().block().size());
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setClientName("Ivan");
        order.setClientPhone("79993452312");
        order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(30);
        order.setDistanceToClient(23);

        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        when(orderRepository.save(order)).thenReturn(Mono.just(order));

        OrderController orderController = new OrderController(orderRepository);

        Mono<Order> response = orderController.createOrder(order);
        assertEquals(order, response.block());
    }

    @Test
    public void testUpdateOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setClientName("Ivan");
        existingOrder.setClientPhone("79993452312");
        existingOrder.setCarModel("Tesla Model S");
        existingOrder.setRequiredKiloWatts(30);
        existingOrder.setDistanceToClient(23);

        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setClientName("Dmitriy");
        updatedOrder.setClientPhone("79507632111");
        updatedOrder.setCarModel("Tesla Cybertruck");
        updatedOrder.setRequiredKiloWatts(30);
        updatedOrder.setDistanceToClient(25);

        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        when(orderRepository.findById(1L)).thenReturn(Mono.just(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(Mono.just(updatedOrder));

        OrderController orderController = new OrderController(orderRepository);

        ResponseEntity<Order> response = orderController.updateOrder(1L, updatedOrder).block();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setClientName("Ivan");
        order.setClientPhone("79993452312");
                   order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(30);
        order.setDistanceToClient(23);

        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        when(orderRepository.findById(1L)).thenReturn(Mono.just(order));
        when(orderRepository.delete(order)).thenReturn(Mono.empty());

        OrderController orderController = new OrderController(orderRepository);

        ResponseEntity<Void> response = orderController.deleteOrder(1L).block();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
