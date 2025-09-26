package am.forex.demo.customer.service.usecase;

import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:39:05
 */
public interface CustomerUseCase {
    Mono<OrderResponse> createOrder(UUID id, OrderRequest request);
}