package am.forex.demo.currency.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:02:46
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "currency", name = "rate")
public class CurrencyRate {
    @Id
    private UUID id;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal rate;
    private LocalDateTime lastUpdated;
}