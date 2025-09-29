package am.forex.demo.customer.api.mapper;

import am.forex.demo.customer.api.dto.CustomerRequest;
import am.forex.demo.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:38:45
 */
@Component
@RequiredArgsConstructor
public class CustomerMapper {

    public Customer toEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setBalance(BigDecimal.ZERO);
        return customer;
    }
}