package am.forex.demo.currency.api.mapper;

import am.forex.demo.currency.domain.entity.CurrencyRate;
import am.forex.demo.shared.dto.rate.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 16:46:39
 */
@Component
@RequiredArgsConstructor
public class CurrencyMapper {
    private final ModelMapper modelMapper;

    public CurrencyRateResponse toResponse(CurrencyRate currencyRate) {
        return modelMapper.map(currencyRate, CurrencyRateResponse.class);
    }
}