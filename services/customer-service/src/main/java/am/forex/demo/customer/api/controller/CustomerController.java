package am.forex.demo.customer.api.controller;

import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.service.usecase.CustomerManagementUseCase;
import am.forex.demo.customer.service.usecase.CustomerUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:06:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerUseCase customerService;
    private final CustomerManagementUseCase customerManagementService;

    @GetMapping("/get/{id}")
    Mono<ResponseEntity<CustomerResponse>> getCustomerById(@PathVariable UUID id) {
        return customerManagementService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/orders/create/{id}")
    Mono<ResponseEntity<OrderResponse>> createOrder(@PathVariable UUID id, @RequestBody OrderRequest request) {
        return customerService.createOrder(id, request)
                .map(ResponseEntity::ok)
                .onErrorResume(IllegalArgumentException.class, e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/order/get/{orderId}")
    Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable UUID orderId) {
        return customerService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/rates/simulate")
    Mono<ResponseEntity<CurrencyRateResponse>> simulateRatesChange() {
        return customerService.simulateRates()
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/get/rates")
    Mono<ResponseEntity<BigDecimal>> getCurrencyRates(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
        return customerService.getCurrencyRates(from, to, amount)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}