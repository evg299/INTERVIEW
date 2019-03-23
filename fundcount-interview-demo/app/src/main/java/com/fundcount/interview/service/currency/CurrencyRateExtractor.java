package com.fundcount.interview.service.currency;

import java.time.LocalDate;

public interface CurrencyRateExtractor {
    Double getUsdRubRate(LocalDate date);
}
