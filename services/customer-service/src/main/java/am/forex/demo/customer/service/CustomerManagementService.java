package am.forex.demo.customer.service;

import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.api.mapper.CustomerMapper;
import am.forex.demo.customer.domain.repository.CustomerRepository;
import am.forex.demo.customer.service.usecase.CustomerManagementUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:43:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerManagementService implements CustomerManagementUseCase {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerResponse> getUserById(UUID userId) {
        return customerRepository.getCustomerById(userId)
                .flatMap(result -> {
                    if (result == null) {
                        log.error("Customer not found for id {}", userId);
                    }
                    return Mono.just(result);
                });
    }
}