package am.forex.demo.shared.dto.order;

import java.math.BigDecimal;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 11:40:34
 */
public record OrderRequest(
        String currencyFrom,
        String currencyTo,
        BigDecimal amount,
        BigDecimal rate) {
}