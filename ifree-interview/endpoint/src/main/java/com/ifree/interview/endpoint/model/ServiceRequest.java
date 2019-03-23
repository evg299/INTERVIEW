package com.ifree.interview.endpoint.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class ServiceRequest {
    @Id
    private Long id;

    private ServiceRequestStatus status;

    private String responseJson;

    private String errorMsg;

    public ServiceRequest(Long id, ServiceRequestStatus status) {
        this.id = id;
        this.status = status;
    }
}
