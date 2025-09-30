package am.forex.demo.order.domain.repository;

import am.forex.demo.order.domain.entity.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:01:04
 */
@Repository
public interface OrderRepository extends R2dbcRepository<Order, UUID> {
}