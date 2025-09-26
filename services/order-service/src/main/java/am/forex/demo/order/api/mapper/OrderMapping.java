package am.forex.demo.order.api.mapper;

import am.forex.demo.order.domain.entity.Order;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:11:29
 */
@Component
@RequiredArgsConstructor
public class OrderMapping {
    private final ModelMapper modelMapper;

    public Order toEntity(OrderRequest request) {
        return modelMapper.map(request, Order.class);
    }

    public OrderResponse toResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}