package com.fundcount.interview.controller;

import com.fundcount.interview.dto.ProfitRequest;
import com.fundcount.interview.dto.ProfitResponse;
import com.fundcount.interview.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    private ProfitService profitService;

    @ResponseBody
    @RequestMapping(value = "recalculate", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfitResponse> recalculate(@RequestBody ProfitRequest request) {
        try {
            BigDecimal profitRUB = profitService.calculate(request.getAsLocalDateBuy(), request.getAsLocalDateSell(), request.getAmountUSD());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ProfitResponse(request, profitRUB.setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProfitResponse(e.getResponseBodyAsString()));
        }
    }
}
