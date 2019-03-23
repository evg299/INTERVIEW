package com.fundcount.interview.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
public class ProfitRequest {
    private BigDecimal amountUSD;
    private Long timestampBuy;
    private Long timestampSell;

    public LocalDate getAsLocalDateBuy() {
        return Instant.ofEpochMilli(timestampBuy).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getAsLocalDateSell() {
        return Instant.ofEpochMilli(timestampSell).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
