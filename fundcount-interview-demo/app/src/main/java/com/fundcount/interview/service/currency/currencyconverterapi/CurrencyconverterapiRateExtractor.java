package com.fundcount.interview.service.currency.currencyconverterapi;

import com.fundcount.interview.service.currency.CurrencyRateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class CurrencyconverterapiRateExtractor implements CurrencyRateExtractor {
    private static final String PAIR_NAME = "USD_RUB";
    private static final String URL_TEMPLATE = "https://free.currencyconverterapi.com/api/v6/convert?q=%s&compact=ultra&date=%s&apiKey=%s";
    private static final DateTimeFormatter URL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${com.currencyconverterapi.apiKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Double getUsdRubRate(LocalDate date) {
        String dateStr = date.format(URL_DATE_TIME_FORMATTER);
        String url = String.format(URL_TEMPLATE, PAIR_NAME, dateStr, apiKey);

        Map<String, Map<String, Double>> restResult = restTemplate.getForObject(url, Map.class);
        return restResult.get(PAIR_NAME).get(dateStr);
    }
}
