package am.forex.demo.order.service.usecase;

import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:59:51
 */
public interface OrderCreationUseCase {
    Mono<OrderResponse> createOrder(UUID customerId, OrderRequest request);
}