package am.forex.demo.customer.api.controller;

import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.service.usecase.CustomerManagementUseCase;
import am.forex.demo.customer.service.usecase.CustomerUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    Mono<CustomerResponse> getById(@PathVariable UUID id) {
        return customerManagementService.getUserById(id);
    }

    @PostMapping("/orders/create/{id}")
    Mono<OrderResponse> createOrder(@PathVariable UUID id, @RequestBody OrderRequest request) {
        return customerService.createOrder(id, request);
    }
}