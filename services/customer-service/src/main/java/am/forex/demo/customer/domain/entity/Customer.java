package am.forex.demo.customer.domain.entity;

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
 * Time: 09:49:49
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "customer", name = "customer")
public class Customer {
    @Id
    private UUID id;
    private String name;
    private String email;
    private BigDecimal balance;
}