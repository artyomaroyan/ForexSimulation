package am.forex.demo.currency.domain.repository;

import am.forex.demo.currency.domain.entity.CurrencyRate;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 16:06:35
 */
@Repository
public interface CurrencyRateRepository extends R2dbcRepository<CurrencyRate, UUID> {
}