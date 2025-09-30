package am.forex.demo.order.api.controller;

import am.forex.demo.order.service.usecase.OrderCreationUseCase;
import am.forex.demo.order.service.usecase.OrderManagementUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:48:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderCreationUseCase orderCreationService;
    private final OrderManagementUseCase orderManagementService;

    @PostMapping("/create/{customerId}")
    Mono<ResponseEntity<OrderResponse>> createOrder(@PathVariable UUID customerId, @RequestBody OrderRequest request) {
        return orderCreationService.createOrder(customerId, request)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/get/{id}")
    Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable UUID id) {
        return orderManagementService.getOrderById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }
}