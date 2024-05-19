package com.company.accountservice.dto.converter;

import com.company.accountservice.model.Account;
import com.company.accountservice.model.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OutboxConverter {

    private final ObjectMapper MAPPER = new ObjectMapper();

    public static Outbox convertToOutbox(Account account) {
        try {
           String payload = MAPPER.writeValueAsString(account);
            return Outbox.builder()
                    .type("Account Created")
                    .payload(payload)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
