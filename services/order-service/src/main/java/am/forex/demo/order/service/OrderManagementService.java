package am.forex.demo.order.service;

import am.forex.demo.order.domain.repository.OrderRepository;
import am.forex.demo.order.service.usecase.OrderManagementUseCase;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:02:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderManagementService implements OrderManagementUseCase {
    private final OrderRepository orderRepository;

    @Override
    public Mono<OrderResponse> getOrderById(UUID id) {
        return orderRepository.getOrderById(id)
                .flatMap(order -> {
                    if (order == null) {
                        log.error("Order not found");
                    }
                    return Mono.just(order);
                });
    }
}