package am.forex.demo.customer.service.usecase;

import am.forex.demo.customer.api.dto.CustomerRequest;
import am.forex.demo.customer.api.dto.CustomerResponse;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:24:29
 */
public interface OnboardingUseCase {
    Mono<CustomerResponse> createNewCustomer(CustomerRequest request);
}