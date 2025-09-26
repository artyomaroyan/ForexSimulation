package am.forex.demo.customer.service;

import am.forex.demo.customer.domain.entity.Customer;
import am.forex.demo.customer.domain.repository.CustomerRepository;
import am.forex.demo.customer.infrastructure.out.OrderClient;
import am.forex.demo.customer.service.usecase.CustomerUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:39:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {
    private final OrderClient orderClient;
    private final CustomerRepository customerRepository;

    @Override
    public Mono<OrderResponse> createOrder(UUID id, OrderRequest request) {
        return checkCustomerBalance(id, request)
                .flatMap(hasEnoughBalance -> {
                    if (!hasEnoughBalance) {
                        return Mono.error(() -> new IllegalArgumentException("Invalid request"));
                    }
                    return decreaseBalance(id, request.amount())
                            .flatMap(newBalance -> orderClient.createOrder(request));
                });
    }

    private Mono<Boolean> checkCustomerBalance(UUID id, OrderRequest request) {
        return customerRepository.getCustomerById(id)
                .flatMap(customer -> {
                    if (customer.balance().compareTo(request.amount()) < 0) {
                        return Mono.error(() -> new IllegalStateException("Customer amount is out of range"));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<BigDecimal> decreaseBalance(UUID id, OrderRequest order) {
        return customerRepository.findById(id)
                .flatMap(customer -> {
                    if (order.currencyFrom().equals("AMD")) {
                        BigDecimal newBalance = customer.getBalance().subtract(order.amount());
                        customer.setBalance(newBalance);
                    }
                    return customerRepository.save(customer)
                            .map(Customer::getBalance);
                });
    }
}