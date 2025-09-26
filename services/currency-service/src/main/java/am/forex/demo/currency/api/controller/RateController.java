package am.forex.demo.currency.api.controller;

import am.forex.demo.currency.service.usecase.CurrencyRateUseCase;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 13:04:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
public class RateController {
    private final CurrencyRateUseCase currencyRateService;

    @PostMapping("/simulate")
    Mono<CurrencyRateResponse> simulateRates() {
        return currencyRateService.simulateRate();
    }

    @GetMapping("/get/current")
    Mono<CurrencyRateResponse> getCurrentRate(String from, String to) {
        return currencyRateService.getCurrentRate(from, to);
    }
}