package am.forex.demo.currency.util;

import am.forex.demo.currency.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 15:28:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateSchedule {
    private final CurrencyRateService currencyRateService;

    @Scheduled(fixedRate = 3600000)
    public void scheduled() {
        currencyRateService.simulateRate()
                .doOnError(e -> log.error("Failed to simulate rates", e))
                .subscribe();
    }
}