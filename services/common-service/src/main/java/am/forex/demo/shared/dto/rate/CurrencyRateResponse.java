package am.forex.demo.shared.dto.rate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 13:05:49
 */
public record CurrencyRateResponse(
        Map<String, BigDecimal> rates,
        LocalDateTime lastUpdated) {
}