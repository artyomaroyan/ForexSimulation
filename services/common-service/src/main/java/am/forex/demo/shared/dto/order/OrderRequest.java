package am.forex.demo.shared.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:40:34
 */
public record OrderRequest(
        UUID customerId,
        String currencyFrom,
        String currencyTo,
        BigDecimal amount,
        BigDecimal rate) {
}