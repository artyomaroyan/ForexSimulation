package am.forex.demo.shared.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:43:58
 */
public record OrderResponse(
        UUID id,
        UUID customerId,
        String currencyFrom,
        String currencyTo,
        BigDecimal amount,
        BigDecimal rate,
        String status
) {
}