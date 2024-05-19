package com.company.accountservice.model;

import com.company.accountservice.model.enums.MailStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Entity(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String mail;
    private String password;
    private MailStatus mailStatus;

    @Column(name = "created_date")
    private Date createdDate;

    @PrePersist
    private void prePersist() {
        createdDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }


}
