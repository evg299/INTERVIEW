package com.fundcount.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
public class ProfitResponse {
    private ProfitRequest request;
    private BigDecimal profitRUB;
    private String errorMsg;

    public ProfitResponse(ProfitRequest request, BigDecimal profitRUB) {
        this.request = request;
        this.profitRUB = profitRUB;
    }

    public ProfitResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
