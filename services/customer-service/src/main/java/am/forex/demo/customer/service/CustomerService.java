package am.forex.demo.customer.service;

import am.forex.demo.customer.application.port.out.CurrencyClientPort;
import am.forex.demo.customer.domain.entity.Customer;
import am.forex.demo.customer.domain.repository.CustomerRepository;
import am.forex.demo.customer.infrastructure.out.OrderClient;
import am.forex.demo.customer.service.usecase.CustomerUseCase;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CurrencyClientPort currencyClient;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Mono<OrderResponse> createOrder(UUID customerId, OrderRequest request) {
        return validateOrderRequest(request)
                .then(Mono.defer(() -> currencyClient.getCurrencyRate(
                        request.currencyFrom(),
                        request.currencyTo(),
                        request.amount())))
                .flatMap(rate -> {
                    OrderRequest newRequest = new OrderRequest(
                            request.currencyFrom(),
                            request.currencyTo(),
                            request.amount(),
                            rate
                    );

                    return checkCustomerBalance(customerId, newRequest)
                            .then(Mono.defer(() -> decreaseBalance(customerId, newRequest)))
                            .then(Mono.defer(() -> orderClient.createOrder(customerId, newRequest)));
                })
                .doOnSuccess(response -> log.info("order created: {}", response))
                .doOnError(error -> log.error("error during order creation: {}", error.getMessage()));
    }

    @Override
    public Mono<OrderResponse> getOrderById(UUID orderId) {
        return orderClient.getOrder(orderId)
                .doOnSuccess(response -> log.info("Order successfully retrieved: {}", response))
                .doOnError(error -> log.error("Error during retrieving order", error));
    }

    @Override
    @Transactional
    public Mono<CurrencyRateResponse> simulateRates() {
        return currencyClient.simulateRates()
                .doOnSuccess(response -> log.info("Rates successfully simulated: {}", response))
                .doOnError(error -> log.error("Error during simulating rates", error));
    }

    @Override
    public Mono<BigDecimal> getCurrencyRates(String from, String to, BigDecimal amount) {
        return currencyClient.getCurrencyRate(from, to, amount)
                .doOnSubscribe(subscription -> log.info("starting currency rates stream for {} to {}", from, to))
                .doOnNext(rate -> log.info("Streaming currency rate: {} to {} = {}", from, to, rate))
                .doOnError(error -> log.error("Error during streaming currency rates", error));
    }

    private Mono<Boolean> validateOrderRequest(OrderRequest request) {
        if (request == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(() -> new IllegalArgumentException("Amount must be greater than zero"));
        }

        if (request.currencyFrom().equals(request.currencyTo())) {
            return Mono.error(() -> new IllegalArgumentException("Currencies must be different"));
        }
        return Mono.just(true);
    }

    private Mono<Boolean> checkCustomerBalance(UUID id, OrderRequest request) {
        return customerRepository.findById(id)
                .flatMap(customer -> convertToDram(request.currencyFrom(), request.amount())
                        .flatMap(amountInDram -> {
                            log.info("Customer balance, {} amount {}", customer.getBalance(), amountInDram);
                            if (customer.getBalance().compareTo(amountInDram) < 0) {
                                return Mono.error(() -> new IllegalStateException("Insufficient balance"));
                            } else {
                                return Mono.just(true);
                            }
                        }));
    }

    private Mono<BigDecimal> decreaseBalance(UUID id, OrderRequest order) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Customer not found")))
                .flatMap(customer -> convertToDram(order.currencyFrom(), order.amount())
                        .flatMap(amountInDram -> {

                            BigDecimal newBalance = customer.getBalance().subtract(amountInDram);
                            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                                return Mono.error(() -> new IllegalStateException("Insufficient balance"));
                            }

                            customer.setBalance(newBalance);
                            return customerRepository.save(customer)
                                    .map(Customer::getBalance);
                        }));
    }

    private Mono<BigDecimal> convertToDram(String from, BigDecimal amount) {
        if (from.equals("AMD")) {
            return Mono.just(amount);
        }
        return currencyClient.getCurrencyRate(from, "AMD", amount)
                .flatMap(rate -> {
                    if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
                        return Mono.error(() -> new IllegalStateException("Invalid Dram convertion rate"));
                    }
                    return Mono.just(amount.multiply(rate));
                });
    }
}