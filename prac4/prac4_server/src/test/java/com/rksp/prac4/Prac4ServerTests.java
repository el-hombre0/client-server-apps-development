package com.rksp.prac4;

import com.rksp.prac4.model.Order;
import com.rksp.prac4.repository.OrderRepository;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Prac4ServerTests {
    @Autowired
    private OrderRepository orderRepository;

    private RSocketRequester requester;

    @BeforeEach
    public void setup() {
        requester = RSocketRequester.builder()
                .rsocketStrategies(builder -> builder.decoder(new Jackson2JsonDecoder()))
                .rsocketStrategies(builder -> builder.encoder(new Jackson2JsonEncoder()))
                .rsocketConnector(connector -> connector
                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
                        .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 5200);
    }

    @AfterEach
    public void cleanup() {
        requester.dispose();
    }

    @Test
    public void testGetOrder() {
        Order order = new Order();
        order.setClientName("Ivanov Ivan");
        order.setClientPhone("79991231234");
        order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(20.0);
        order.setDistanceToClient(13.5);
        order.setCost(5200.0);

        Order savedOrder = orderRepository.save(order);

        Mono<Order> result = requester.route("getOrder")
                .data(savedOrder.getId())
                .retrieveMono(Order.class);

        assertNotNull(result.block());
    }

    @Test
    public void testAddOrder() {
        Order order = new Order();
        order.setClientName("Ivanov Ivan");
        order.setClientPhone("79991231234");
        order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(20.0);
        order.setDistanceToClient(13.5);
        order.setCost(5200.0);

        Mono<Order> result = requester.route("addOrder")
                .data(order)
                .retrieveMono(Order.class);

        Order savedOrder = result.block();
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertTrue(savedOrder.getId() > 0);
    }

    @Test
    public void testGetOrders() {
        Flux<Order> result = requester.route("getOrders")
                .retrieveFlux(Order.class);

        assertNotNull(result.blockFirst());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        order.setClientName("Ivanov Ivan");
        order.setClientPhone("79991231234");
        order.setCarModel("Tesla Model S");
        order.setRequiredKiloWatts(20.0);
        order.setDistanceToClient(13.5);
        order.setCost(5200.0);

        Order savedOrder = orderRepository.save(order);

        Mono<Void> result = requester.route("deleteOrder")
                .data(savedOrder.getId())
                .send();

        result.block();
        Order deletedOrder = orderRepository.findOrderById(savedOrder.getId());
        assertNotSame(deletedOrder, savedOrder);
    }
}
