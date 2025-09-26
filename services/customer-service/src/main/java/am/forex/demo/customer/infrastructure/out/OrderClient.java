package am.forex.demo.customer.infrastructure.out;

import am.forex.demo.customer.application.port.out.OrderClientPort;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:47:00
 */
@Slf4j
@Service
public class OrderClient implements OrderClientPort {
    private final WebClient webClient;

    public OrderClient(@Qualifier("orderWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<OrderResponse> createOrder(OrderRequest request) {
        return webClient.post()
                .uri("/api/v1/orders/create")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    @Override
    public Mono<OrderResponse> getOrder(UUID orderId) {
        return webClient.get()
                .uri("/api/v1/orders/get/{orderId}")
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }
}