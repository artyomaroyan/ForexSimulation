package am.forex.demo.currency.service.usecase;

import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 15:09:46
 */
public interface CurrencyRateUseCase {
    Mono<CurrencyRateResponse> simulateRate();
    Flux<BigDecimal> getCurrencyRates(String from, String to);
    Mono<BigDecimal> fetchCurrentRate(String from, String to);
}