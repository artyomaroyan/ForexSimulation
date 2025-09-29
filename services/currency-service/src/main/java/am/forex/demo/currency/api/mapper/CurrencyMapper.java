package am.forex.demo.currency.api.mapper;

import am.forex.demo.currency.domain.entity.CurrencyRate;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 16:46:39
 */
@Component
@RequiredArgsConstructor
public class CurrencyMapper {

    public CurrencyRateResponse toResponse(CurrencyRate currencyRate) {
        Map<String, BigDecimal> rate = Map.of(
                  currencyRate.getCurrencyFrom() + " - " + currencyRate.getCurrencyTo(),
                    currencyRate.getRate()
        );
        return new CurrencyRateResponse(rate, currencyRate.getLastUpdated());
    }
}