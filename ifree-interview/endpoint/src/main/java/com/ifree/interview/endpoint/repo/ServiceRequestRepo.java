package com.ifree.interview.endpoint.repo;

import com.ifree.interview.endpoint.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepo extends CrudRepository<ServiceRequest, Long> {
}
