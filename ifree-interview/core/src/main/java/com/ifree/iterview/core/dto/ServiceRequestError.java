package com.ifree.iterview.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceRequestError {
    private Long id;
    private String errorMsg;
}
