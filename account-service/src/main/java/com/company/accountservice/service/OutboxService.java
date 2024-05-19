package com.company.accountservice.service;

import com.company.accountservice.model.Outbox;
import com.company.accountservice.publisher.KafkaPublisher;
import com.company.accountservice.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final KafkaPublisher kafkaPublisher;

    private final ObjectMapper MAPPER = new ObjectMapper();

    public OutboxService(OutboxRepository outboxRepository, KafkaPublisher kafkaPublisher) {
        this.outboxRepository = outboxRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    public void createOutbox(Outbox outbox) {
        outboxRepository.save(outbox);
    }

    public void debeziumDatabaseChange(Map<String, Object> payload) {
        log.info("OutboxService -> debeziumDatabaseChange - Debezium payload {} ", payload);
        try {
            kafkaPublisher.publish("account-created", MAPPER.writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Outbox> findAll() {
        return outboxRepository.findAll();
    }

    public void deleteById(String id) {
        log.info("Deleted From Outbox Table ById {}: ", id);
        log.info("Outbox id {}", findMatchingIdInPayload(id));
        outboxRepository.deleteById(findMatchingIdInPayload(id));
    }

    public String findMatchingIdInPayload(String targetId) {
        List<Outbox> outboxList = findAll();

        for (Outbox outbox : outboxList) {
            String payload = outbox.getPayload();
            try {
                // Payload JSON
                JsonNode jsonNode = new ObjectMapper().readTree(payload);
                String payloadId = jsonNode.get("id").asText();

                if (targetId.equals(payloadId)) {
                    return outbox.getId();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
