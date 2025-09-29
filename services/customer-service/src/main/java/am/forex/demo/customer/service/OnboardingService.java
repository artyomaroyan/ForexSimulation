package am.forex.demo.customer.service;

import am.forex.demo.customer.api.dto.CustomerRequest;
import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.api.mapper.CustomerMapper;
import am.forex.demo.customer.domain.entity.Customer;
import am.forex.demo.customer.domain.repository.CustomerRepository;
import am.forex.demo.customer.service.usecase.OnboardingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:28:32
 */
@Service
@RequiredArgsConstructor
public class OnboardingService implements OnboardingUseCase {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Mono<CustomerResponse> createNewCustomer(CustomerRequest request) {
        return customerRepository.findByEmail(request.email())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Customer with email " + request.email() + " already exists"));

                    } else {
                        Customer customer = customerMapper.toEntity(request);
                        return customerRepository.save(customer)
                                .map(customerMapper::toResponse);
                    }
                });
    }
}