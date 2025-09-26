package am.forex.demo.currency.service.usecase;

import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 15:09:46
 */
public interface CurrencyRateUseCase {
    Mono<CurrencyRateResponse> simulateRate();
    Mono<CurrencyRateResponse> getCurrentRate(String from, String to);
}