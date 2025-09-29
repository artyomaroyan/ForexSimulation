package am.forex.demo.order.api.mapper;

import am.forex.demo.order.domain.entity.Order;
import am.forex.demo.order.domain.enums.Status;
import am.forex.demo.shared.dto.order.OrderRequest;
import am.forex.demo.shared.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 12:11:29
 */
@Component
@RequiredArgsConstructor
public class OrderMapping {

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(request.customerId());
        order.setCurrencyFrom(request.currencyFrom());
        order.setCurrencyTo(request.currencyTo());
        order.setAmount(request.amount());
        order.setRate(request.rate());
        order.setStatus(Status.NEW);
        return order;
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getCurrencyFrom(),
                order.getCurrencyTo(),
                order.getAmount(),
                order.getRate(),
                order.getStatus().name()
        );
    }
}