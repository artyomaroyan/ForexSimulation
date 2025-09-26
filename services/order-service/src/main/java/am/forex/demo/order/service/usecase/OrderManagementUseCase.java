package am.forex.demo.order.service.usecase;

import am.forex.demo.shared.dto.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:02:13
 */
public interface OrderManagementUseCase {
    Mono<OrderResponse> getOrderById(UUID id);
}