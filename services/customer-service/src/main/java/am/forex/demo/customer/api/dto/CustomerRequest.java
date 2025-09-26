package am.forex.demo.customer.api.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:25:32
 */
@Validated
public record CustomerRequest(
        @NotBlank String name,
        @NotBlank String email) {
}