package am.forex.demo.customer.service.usecase;

import am.forex.demo.customer.api.dto.CustomerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:43:09
 */
public interface CustomerManagementUseCase {
    Mono<CustomerResponse> getUserById(UUID id);
}