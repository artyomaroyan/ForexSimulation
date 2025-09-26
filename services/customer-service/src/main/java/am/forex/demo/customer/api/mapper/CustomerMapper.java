package am.forex.demo.customer.api.mapper;

import am.forex.demo.customer.api.dto.CustomerRequest;
import am.forex.demo.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:38:45
 */
@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public Customer toEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }
}