package am.forex.demo.order.service;

import am.forex.demo.order.api.mapper.OrderMapping;
import am.forex.demo.order.domain.entity.Order;
import am.forex.demo.order.domain.repository.OrderRepository;
import am.forex.demo.order.service.usecase.OrderCreationUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:00:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService implements OrderCreationUseCase {
    private final OrderMapping orderMapping;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Mono<OrderResponse> createOrder(UUID customerId, OrderRequest request) {
        Order newOrder = orderMapping.toEntity(customerId, request);
        return orderRepository.save(newOrder)
                .map(orderMapping::toResponse)
                .doOnSuccess(response -> log.info("Order successfully created: {}", response))
                .doOnError(error -> log.error("Failed to create order", error));
    }
}