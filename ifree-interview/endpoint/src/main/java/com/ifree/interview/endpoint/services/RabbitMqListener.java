package com.ifree.interview.endpoint.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifree.interview.endpoint.model.ServiceRequest;
import com.ifree.interview.endpoint.model.ServiceRequestStatus;
import com.ifree.interview.endpoint.repo.ServiceRequestRepo;
import com.ifree.iterview.core.dto.AbstractServiceRequest;
import com.ifree.iterview.core.dto.ServiceRequestError;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableRabbit
@Component
public class RabbitMqListener {
    public static final Logger LOGGER = Logger.getGlobal();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ServiceRequestRepo serviceRequestRepo;

    @RabbitListener(queues = "${com.ifree.queues.responses}")
    public void processResponse(String message) {
        LOGGER.info("Received response: " + message);

        try {
            AbstractServiceRequest abstractServiceRequest = objectMapper.readValue(message, AbstractServiceRequest.class);

            Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepo.findById(abstractServiceRequest.getId());
            if (optionalServiceRequest.isPresent()) {
                ServiceRequest serviceRequest = optionalServiceRequest.get();
                serviceRequest.setStatus(ServiceRequestStatus.DONE);
                serviceRequest.setResponseJson(message);
                serviceRequestRepo.save(serviceRequest);
            }
        } catch (IOException e) {
            LOGGER.log(Level.ALL, e.getMessage());
        }

    }

    @RabbitListener(queues = "${com.ifree.queues.errors}")
    public void processError(String message) {
        LOGGER.info("Received error: " + message);

        try {
            ServiceRequestError requestError = objectMapper.readValue(message, ServiceRequestError.class);

            Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepo.findById(requestError.getId());
            if (optionalServiceRequest.isPresent()) {
                ServiceRequest serviceRequest = optionalServiceRequest.get();
                serviceRequest.setStatus(ServiceRequestStatus.ERROR);
                serviceRequest.setResponseJson(null);
                serviceRequestRepo.save(serviceRequest);
            }
        } catch (IOException e) {
            LOGGER.log(Level.ALL, e.getMessage());
        }
    }
}