package com.ifree.interview.endpoint.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifree.interview.endpoint.model.ServiceRequest;
import com.ifree.interview.endpoint.model.ServiceRequestStatus;
import com.ifree.interview.endpoint.repo.ServiceRequestRepo;
import com.ifree.iterview.core.dto.AbstractServiceRequest;
import com.ifree.iterview.core.dto.Type1ServiceRequest;
import com.ifree.iterview.core.dto.Type2ServiceRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ServiceRequestRepo serviceRequestRepo;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${com.ifree.queues.requests}")
    private String requestsQueue;

    @ResponseBody
    @RequestMapping(value = "checkType1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Type1ServiceRequest> checkType1() {
        Type1ServiceRequest result = new Type1ServiceRequest();
        result.setId(1);
        result.setName("qweqweqwe");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ResponseBody
    @RequestMapping(value = "checkType2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Type2ServiceRequest> checkType2() {
        Type2ServiceRequest result = new Type2ServiceRequest();
        result.setId(2);
        result.setAmount(BigDecimal.TEN);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ResponseBody
    @RequestMapping(value = "checkRequest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkRequest() {
        amqpTemplate.convertAndSend(requestsQueue, "Message to queue");
        return ResponseEntity.status(HttpStatus.OK).body("qwe qwe qwe");
    }

    @ResponseBody
    @RequestMapping(value = "acceptRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> acceptRequest(@RequestBody AbstractServiceRequest abstractServiceRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(abstractServiceRequest);

        amqpTemplate.convertAndSend(requestsQueue, json);
        serviceRequestRepo.save(new ServiceRequest(abstractServiceRequest.getId(), ServiceRequestStatus.ACCEPTED));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(abstractServiceRequest.getId());
    }

    @ResponseBody
    @RequestMapping(value = "getResult/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbstractServiceRequest> getResult(@PathVariable Long id) throws IOException {
        Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepo.findById(id);
        if (!optionalServiceRequest.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        ServiceRequest serviceRequest = optionalServiceRequest.get();
        switch (serviceRequest.getStatus()) {
            case DONE:
                String responseJson = serviceRequest.getResponseJson();
                return ResponseEntity.status(HttpStatus.OK).body(objectMapper.readValue(responseJson, AbstractServiceRequest.class));
            case ERROR:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            default:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
}
