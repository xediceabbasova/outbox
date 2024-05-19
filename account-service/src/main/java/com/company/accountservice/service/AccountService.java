package com.company.accountservice.service;

import com.company.accountservice.dto.CreateAccountDto;
import com.company.accountservice.dto.converter.AccountConverter;
import com.company.accountservice.dto.converter.OutboxConverter;
import com.company.accountservice.model.Account;
import com.company.accountservice.model.enums.MailStatus;
import com.company.accountservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final OutboxService outboxService;

    public AccountService(AccountRepository accountRepository, OutboxService outboxService) {
        this.accountRepository = accountRepository;
        this.outboxService = outboxService;
    }

    public Account createAccount(CreateAccountDto dto) {
        Account newAccount = AccountConverter.fromDto(dto);
        newAccount.setMailStatus(MailStatus.CREATED);
        Account saveAccount = accountRepository.save(newAccount);
        log.info("AccountService -> createAccount Account create {}", saveAccount);

        outboxService.createOutbox(OutboxConverter.convertToOutbox(saveAccount));
        return saveAccount;
    }


    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
