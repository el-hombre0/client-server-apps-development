package rtu.rksp.electrocars.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import rtu.rksp.electrocars.model.Order;

@Repository
public interface OrderRepository extends R2dbcRepository<Order, Long> {
}
