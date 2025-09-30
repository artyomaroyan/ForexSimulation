package am.forex.demo.currency.api.controller;

import am.forex.demo.currency.service.usecase.CurrencyRateUseCase;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

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
    Mono<BigDecimal> getCurrentRate(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
        return currencyRateService.fetchCurrentRate(from, to, amount);
    }
}