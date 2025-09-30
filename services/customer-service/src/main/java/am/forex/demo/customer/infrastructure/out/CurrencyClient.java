package am.forex.demo.customer.infrastructure.out;

import am.forex.demo.customer.application.port.out.CurrencyClientPort;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 21:23:20
 */
@Slf4j
@Component
public class CurrencyClient implements CurrencyClientPort {
    private final WebClient webClient;

    public CurrencyClient(@Qualifier("currencyWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<CurrencyRateResponse> simulateRates() {
        return webClient.post()
                .uri("/api/v1/rates/simulate")
                .retrieve()
                .bodyToMono(CurrencyRateResponse.class);
    }

    @Override
    public Mono<BigDecimal> getCurrencyRate(String from, String to, BigDecimal amount) {
        return webClient.get()
                .uri(uribuilder -> uribuilder
                        .path("/api/v1/rates/get/current")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .bodyToMono(BigDecimal.class);
    }
}