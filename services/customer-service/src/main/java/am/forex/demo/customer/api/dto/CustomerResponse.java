package am.forex.demo.customer.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:29:41
 */
public record CustomerResponse(
        UUID id,
        String name,
        String email,
        BigDecimal balance
) {
}