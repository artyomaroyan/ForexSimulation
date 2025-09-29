package am.forex.demo.customer.service.usecase;

import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:39:05
 */
public interface CustomerUseCase {
    Mono<OrderResponse> createOrder(UUID id, OrderRequest request);
    Mono<CurrencyRateResponse> simulateRates();
    Flux<BigDecimal> getCurrencyRates(String from, String to);
}