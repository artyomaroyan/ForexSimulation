package am.forex.demo.customer.application.port.out;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 21:22:22
 */
public interface CurrencyClientPort {
    Mono<BigDecimal> getCurrencyRate(String from, String to);
}