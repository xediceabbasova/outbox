package com.company.accountservice.controller;

import com.company.accountservice.model.Outbox;
import com.company.accountservice.service.OutboxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/outboxes")
public class OutboxController {

    private final OutboxService outboxService;

    public OutboxController(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @GetMapping
    public List<Outbox> getOutboxes(){
        return outboxService.findAll();
    }
}
