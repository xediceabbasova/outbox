package com.company.mailservice.service;

import com.company.mailservice.publisher.KafkaPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    private final KafkaPublisher kafkaPublisher;

    public MailService(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    public void sendMail(String username) {
        log.info("MailService -> sendMail -> 200");
        log.info("MailService -> sendMail -> Hello, {} Nice to meet you !!", username);
    }

    public void deleteProcessByIdFromOutbox(String id) {
        log.info("MailService -> deleteProcessByIdFromOutbox ->");
        kafkaPublisher.publish("delete-process-byId-from-outbox", id);
    }
}
