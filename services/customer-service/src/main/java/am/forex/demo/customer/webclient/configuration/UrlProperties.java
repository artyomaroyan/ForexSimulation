package am.forex.demo.customer.webclient.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:09:56
 */
@Validated
@ConfigurationProperties("webclient.base-url")
public record UrlProperties(
        @NotBlank String orderUrl,
        @NotBlank String currencyUrl) {
}