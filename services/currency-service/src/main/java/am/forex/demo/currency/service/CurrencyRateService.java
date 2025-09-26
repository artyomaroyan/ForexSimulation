package am.forex.demo.currency.service;

import am.forex.demo.currency.api.mapper.CurrencyMapper;
import am.forex.demo.currency.domain.entity.CurrencyRate;
import am.forex.demo.currency.domain.repository.CurrencyRateRepository;
import am.forex.demo.currency.service.usecase.CurrencyRateUseCase;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 15:10:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRateService implements CurrencyRateUseCase {
    private static final Random RANDOM = new Random();
    private static final BigDecimal MIN_CHANGED_PERCENT = BigDecimal.valueOf(0.5);
    private static final BigDecimal MAX_CHANGE_PERCENT = BigDecimal.valueOf(0.10);
    private static final String BASE_CURRENCY = "AMD";

    private final CurrencyRateRepository currencyRateRepository;
    private final Map<String, BigDecimal> rates = new HashMap<>();
    private final CurrencyMapper currencyMapper;

    @PostConstruct
    void initializeRates() {
        rates.put("USD", BigDecimal.valueOf(380.00));
        rates.put("EUR", BigDecimal.valueOf(439.50));
        rates.put("GBP", BigDecimal.valueOf(501.00));
        rates.put("JPY", BigDecimal.valueOf(2.43));
    }

    @Override
    public Mono<CurrencyRateResponse> simulateRate() {
        rates.replaceAll((currency, currencyRate) -> calculateNewRate(currencyRate));

        return Flux.fromIterable(rates.entrySet())
                .flatMap(entry -> {
                    String currency = entry.getKey();
                    BigDecimal value = entry.getValue();

                    CurrencyRate rate1 = new CurrencyRate(UUID.randomUUID(), BASE_CURRENCY, currency, value, LocalDateTime.now());
                    CurrencyRate rate2 = new CurrencyRate(UUID.randomUUID(), currency, BASE_CURRENCY, value, LocalDateTime.now());

                    return currencyRateRepository.saveAll(List.of(rate1, rate2)).then();
                })
                .then(Mono.fromSupplier(() -> {
                    log.info("Simulate currency rate {}", rates);
                    return new CurrencyRateResponse(rates, LocalDateTime.now());
                }));
    }

    @Override
    public Mono<CurrencyRateResponse> getCurrentRate(String from, String to) {
        return currencyRateRepository.getCurrencyRate(from, to)
                .flatMap(currentRate -> {
                    var response = currencyMapper.toResponse(currentRate);
                    return Mono.just(response);
                });
    }

    private BigDecimal calculateNewRate(BigDecimal currentRate) {
        double changePercent = (RANDOM.nextDouble() - MIN_CHANGED_PERCENT.doubleValue()) * MAX_CHANGE_PERCENT.doubleValue();
        BigDecimal newRate = currentRate.multiply(BigDecimal.valueOf(changePercent));
        return currentRate.add(newRate).setScale(4, RoundingMode.HALF_EVEN);
    }
}