package com.ifree.iterview.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorServiceRequest {
    private Long id;
    private String errorMsg;
}
