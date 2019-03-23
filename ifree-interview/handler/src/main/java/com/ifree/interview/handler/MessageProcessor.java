package com.ifree.interview.handler;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifree.iterview.core.dto.AbstractServiceRequest;
import com.ifree.iterview.core.dto.ErrorServiceRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableRabbit
@Component
public class MessageProcessor {
    private static final Logger LOGGER = Logger.getGlobal();

    public static final Random RANDOM = new Random(System.nanoTime());

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${com.ifree.queues.responses}")
    private String responsesQueue;
    @Value("${com.ifree.queues.errors}")
    private String errorsQueue;


    private ObjectMapper createObjectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RabbitListener(queues = "${com.ifree.queues.requests}")
    public void process(String message) throws IOException {
        LOGGER.log(Level.INFO, message);
        ObjectMapper objectMapper = createObjectMapper();

        AbstractServiceRequest abstractServiceRequest = objectMapper.readValue(message, AbstractServiceRequest.class);

        try {
            processRequest(abstractServiceRequest);
            amqpTemplate.convertAndSend(responsesQueue, message);
        } catch (Exception e) {
            String errorJson = objectMapper.writeValueAsString(new ErrorServiceRequest(abstractServiceRequest.getId(), e.getMessage()));
            amqpTemplate.convertAndSend(errorsQueue, errorJson);
        }

    }

    private AbstractServiceRequest processRequest(AbstractServiceRequest abstractServiceRequest) throws Exception {
        Thread.sleep(1000);

        if (1 == RANDOM.nextInt(20)) {
            throw new Exception("Some exception");
        }

        return abstractServiceRequest;
    }
}