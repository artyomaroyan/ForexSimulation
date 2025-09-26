package am.forex.demo.currency.domain.repository;

import am.forex.demo.currency.domain.entity.CurrencyRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 16:06:35
 */
@Repository
public interface CurrencyRateRepository extends R2dbcRepository<CurrencyRate, UUID> {
    @Query("""
SELECT r.rate FROM currency.rate r
WHERE r.last_updated <= CURRENT_TIMESTAMP
ORDER BY r.last_updated AND r.currency_from = :from AND r.currency_to = :to DESC LIMIT 1
""")
    Mono<CurrencyRate> getCurrencyRate(String from, String to);
}