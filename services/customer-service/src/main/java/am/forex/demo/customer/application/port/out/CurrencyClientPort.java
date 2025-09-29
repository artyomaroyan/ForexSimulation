package am.forex.demo.customer.application.port.out;

import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 21:22:22
 */
public interface CurrencyClientPort {
    Mono<CurrencyRateResponse> simulateRates();
    Flux<BigDecimal> getCurrencyRate(String from, String to);
}