package com.fundcount.interview.service;

import com.fundcount.interview.service.currency.CurrencyRateExtractor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ProfitService {
    @Autowired
    @Qualifier("currencyconverterapiRateExtractor")
    private CurrencyRateExtractor currencyRateExtractor;

    @Setter
    @Value("${com.fundcount.interview.ProfitService.spread}")
    private Double spread;

    public BigDecimal calculate(LocalDate buyDate, LocalDate sellDate, BigDecimal amountUSD) {
        Double usdRubRateBuy = currencyRateExtractor.getUsdRubRate(buyDate);
        Double usdRubRateSell = currencyRateExtractor.getUsdRubRate(sellDate);

        usdRubRateBuy *= (1 + spread);
        usdRubRateSell *= (1 - spread);

        return BigDecimal.valueOf(usdRubRateSell - usdRubRateBuy).multiply(amountUSD);
    }
}
