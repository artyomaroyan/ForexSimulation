package am.forex.demo.currency.service;

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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private static final String BASE_CURRENCY = "AMD";
    private static final BigDecimal MIN_CHANGE_PERCENT = BigDecimal.valueOf(0.1);
    private static final BigDecimal MAX_CHANGE_PERCENT = BigDecimal.valueOf(0.5);

    private final CurrencyRateRepository currencyRateRepository;
    private final Map<String, BigDecimal> rates = new ConcurrentHashMap<>();

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
                    BigDecimal rate = entry.getValue();

                    CurrencyRate baseRateToCurrencyRate = new CurrencyRate(UUID.randomUUID(), BASE_CURRENCY, currency, rate, LocalDateTime.now());
                    CurrencyRate currencyRateToBAseRate = new CurrencyRate(UUID.randomUUID(), currency, BASE_CURRENCY, rate, LocalDateTime.now());

                    return currencyRateRepository.saveAll(List.of(baseRateToCurrencyRate, currencyRateToBAseRate));
                })
                .then()
                .thenReturn(new CurrencyRateResponse(new HashMap<>(rates), LocalDateTime.now()));
    }

    @Override
    public Flux<BigDecimal> getCurrencyRates(String from, String to) {
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> fetchCurrentRate(from, to))
                .doOnSubscribe(subscription -> log.info("Starting currency rates stream for {} to {}", from, to))
                .doOnNext(rate -> log.debug("Current rate {} for {} is {}", rate, from, to))
                .doOnError(error -> log.error("Error during streaming currency rates", error));
    }

    @Override
    public Mono<BigDecimal> fetchCurrentRate(String from, String to) {
        return Mono.fromCallable(() -> calculateRate(from, to))
                .doOnSuccess(rate -> log.info("Fetched current rate from {} to {}: {}", from, to, rate))
                .doOnError(error -> log.error("Error fetching current rate from {} to {}", from, to, error));
    }

    private BigDecimal calculateRate(String from, String to) {
        if (BASE_CURRENCY.equals(from) && rates.containsKey(to)) {
            return rates.get(to);

        } else if (BASE_CURRENCY.equals(to) && rates.containsKey(from)) {
            return BigDecimal.ONE.divide(rates.get(from), 4, RoundingMode.HALF_EVEN);

        } else if (rates.containsKey(from) && rates.containsKey(to)) {
            BigDecimal fromRate = rates.get(from);
            BigDecimal toRate = rates.get(to);
            return toRate.divide(fromRate, 4, RoundingMode.HALF_EVEN);
        }
        throw new IllegalArgumentException("Unsupported currency pair: " + from + " to " + to);
    }

    private BigDecimal calculateNewRate(BigDecimal currentRate) {
        double changePercent = (RANDOM.nextDouble() - MIN_CHANGE_PERCENT.doubleValue()) * MAX_CHANGE_PERCENT.doubleValue();
        BigDecimal newRate = currentRate.multiply(BigDecimal.valueOf(changePercent));
        return currentRate.add(newRate).setScale(4, RoundingMode.HALF_EVEN);
    }
}