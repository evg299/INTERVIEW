package com.fundcount.interview.service;

import com.fundcount.interview.service.currency.CurrencyRateExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfitServiceTest {
    @Mock
    private CurrencyRateExtractor currencyRateExtractor;

    @InjectMocks
    private ProfitService profitService;

    @Test
    public void calculate() {
        LocalDate buy = LocalDate.of(2017, 1, 1);
        LocalDate sell = LocalDate.of(2018, 1, 1);
        profitService.setSpread(0.005);

        when(currencyRateExtractor.getUsdRubRate(buy)).thenReturn(2.0);
        when(currencyRateExtractor.getUsdRubRate(sell)).thenReturn(3.0);

        BigDecimal profit = profitService.calculate(buy, sell, BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        assertEquals(BigDecimal.valueOf(0.98), profit);
    }
}