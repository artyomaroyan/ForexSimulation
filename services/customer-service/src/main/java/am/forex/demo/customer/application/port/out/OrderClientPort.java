package am.forex.demo.customer.application.port.out;

import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:37:59
 */
public interface OrderClientPort {
    Mono<OrderResponse> createOrder(OrderRequest request);
    Mono<OrderResponse> getOrder(UUID orderId);
}