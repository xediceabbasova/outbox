package com.company.accountservice.repository;

import com.company.accountservice.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, String> {
}
