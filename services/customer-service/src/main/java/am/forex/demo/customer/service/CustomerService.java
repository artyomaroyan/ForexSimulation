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
import reactor.core.publisher.Flux;
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
    public Mono<OrderResponse> createOrder(UUID customerId, OrderRequest request) {
        return checkCustomerBalance(customerId, request)
                .flatMap(hasEnoughBalance -> {
                    if (!hasEnoughBalance) {
                        return Mono.error(() -> new IllegalArgumentException("Invalid request"));
                    }
                    return decreaseBalance(customerId, request)
                            .flatMap(newBalance -> orderClient.createOrder(request));
                });
    }

    @Override
    public Mono<OrderResponse> getOrderById(UUID orderId) {
        return orderClient.getOrder(orderId)
                .doOnSuccess(response -> log.info("Order successfully retrieved: {}", response))
                .doOnError(error -> log.error("Error during retrieving order", error));
    }

    @Override
    public Mono<CurrencyRateResponse> simulateRates() {
        return currencyClient.simulateRates()
                .doOnSuccess(response -> log.info("Rates successfully simulated: {}", response))
                .doOnError(error -> log.error("Error during simulating rates", error));
    }

    @Override
    public Flux<BigDecimal> getCurrencyRates(String from, String to) {
        return currencyClient.getCurrencyRate(from, to)
                .repeat()
                .doOnSubscribe(subscription -> log.info("starting currency rates stream for {} to {}", from, to))
                .doOnNext(rate -> log.info("Streaming currency rate: {} to {} = {}", from, to, rate))
                .doOnError(error -> log.error("Error during streaming currency rates", error));
    }

    private Mono<Boolean> checkCustomerBalance(UUID id, OrderRequest request) {
        return customerRepository.findById(id)
                .flatMap(customer -> {
                    if (customer.getBalance().compareTo(request.amount()) < 0) {
                        return Mono.error(() -> new IllegalStateException("Customer amount is out of range"));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<BigDecimal> decreaseBalance(UUID id, OrderRequest order) {
        if (order.currencyFrom().equals(order.currencyTo())) {
            return Mono.error(new IllegalStateException("Invalid request"));
        }

        return customerRepository.findById(id)
                .flatMap(customer -> currencyClient.getCurrencyRate(order.currencyFrom(), order.currencyTo())
                        .flatMap(rate -> {
                            if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
                                return Mono.error(new IllegalStateException("Invalid exchange rate"));
                            }

                            BigDecimal amountFrom = order.amount();
                            Mono<BigDecimal> amountInDram;

                            if (order.currencyFrom().equals("AMD")) {
                                amountInDram = Mono.just(amountFrom);
                            } else {
                                amountInDram = currencyClient.getCurrencyRate(order.currencyFrom(), "AMD")
                                        .flatMap(fromToAmdRate -> {
                                            if (fromToAmdRate == null || fromToAmdRate.compareTo(BigDecimal.ZERO) <= 0) {
                                                return Mono.error(new IllegalStateException("Invalid AMD conversion rate"));
                                            }
                                            return Mono.just(amountFrom.multiply(fromToAmdRate));
                                        });
                            }

                            return amountInDram.flatMap(dram -> {
                                customer.setBalance(customer.getBalance().subtract(dram));

                                return customerRepository.save(customer)
                                        .map(Customer::getBalance);
                            });
                        })
                );
    }
}