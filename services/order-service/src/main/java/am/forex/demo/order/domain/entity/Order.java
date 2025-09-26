package am.forex.demo.order.domain.entity;

import am.forex.demo.order.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 09:54:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "orders", name = "orders")
public class Order {
    @Id
    private UUID id;
    private UUID customerId;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amount;
    private BigDecimal rate;
    private Status status;
}