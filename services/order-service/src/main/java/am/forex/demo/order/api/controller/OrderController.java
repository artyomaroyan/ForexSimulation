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

    @PostMapping("/create")
    ResponseEntity<Mono<OrderResponse>> createOrder(OrderRequest request) {
        var result = orderCreationService.createOrder(request);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    Mono<OrderResponse> getOrderById(@PathVariable UUID id) {
        return orderManagementService.getOrderById(id);
    }
}