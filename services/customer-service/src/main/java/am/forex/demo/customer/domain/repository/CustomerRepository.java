package am.forex.demo.customer.domain.repository;

import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.domain.entity.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:29:00
 */
@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, UUID> {
    Mono<CustomerResponse> getCustomerById(UUID id);
    Mono<CustomerResponse> getCustomerByEmail(String email);
}