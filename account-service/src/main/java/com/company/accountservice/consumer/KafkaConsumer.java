package com.company.accountservice.consumer;

import com.company.accountservice.service.OutboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private static final String TOPIC_NAME = "delete-process-byId-from-outbox";
    private static final String GROUP_ID = "GroupId";
    private static final String CONTAINER_FACTORY = "ContainerFactory";

    private final OutboxService outboxService;

    public KafkaConsumer(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void consumeMessage(String message) {
        log.info("Received message: " + message);
        outboxService.deleteById(message);
    }
}