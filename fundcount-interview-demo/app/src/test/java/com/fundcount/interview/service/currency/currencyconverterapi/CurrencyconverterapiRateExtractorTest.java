package com.fundcount.interview.service.currency.currencyconverterapi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CurrencyconverterapiRateExtractorTest {

    @Autowired
    private CurrencyconverterapiRateExtractor rateExtractor;

    @Test
    public void getUsdRubRate() {
        Double usdRubRate = rateExtractor.getUsdRubRate(LocalDate.now().minusMonths(1));
        Assert.assertNotNull(usdRubRate);
    }
}