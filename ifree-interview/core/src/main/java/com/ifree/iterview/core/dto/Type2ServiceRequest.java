package com.ifree.iterview.core.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Type2ServiceRequest extends AbstractServiceRequest {
    private BigDecimal amount;
}
