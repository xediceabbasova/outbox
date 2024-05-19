package com.company.mailservice.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.mailservice.model.KafkaPayload;
import com.company.mailservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class KafkaConsumer {

    private static final String TOPIC_NAME = "account-created";
    private static final String GROUP_ID = "GroupId";
    private static final String CONTAINER_FACTORY = "ContainerFactory";

    private final MailService mailService;
    private final ObjectMapper MAPPER = new ObjectMapper();

    public KafkaConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void listener(@Payload Object event, ConsumerRecord consumerRecord) throws Exception {
        // Kafka kaydinin degerini bir dize olarak al
        String value = (String) consumerRecord.value();

        // Deger dizesini JSON dugumune donustur
        JsonNode payload = MAPPER.readTree(value);
        log.info("JSON NODE: {}", payload);

        // JSON'dan KafkaPayload nesnesine donusturme islemi
        KafkaPayload kafkaPayload = MAPPER.readValue(payload.get("payload").asText(), KafkaPayload.class);
        log.info("KafkaPayload: {}", kafkaPayload);
        log.info("KafkaPayload getId: {}", kafkaPayload.getId());

        // E-posta servisini kullanarak e-posta gonderme islemi
        mailService.sendMail(kafkaPayload.getUsername());

        // Outbox'tan belirli bir islemi silme islemi
        mailService.deleteProcessByIdFromOutbox(kafkaPayload.getId());
    }

}
