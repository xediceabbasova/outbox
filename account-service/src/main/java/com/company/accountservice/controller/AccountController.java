package com.company.accountservice.controller;

import com.company.accountservice.dto.CreateAccountDto;
import com.company.accountservice.model.Account;
import com.company.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountDto dto) {
        return accountService.createAccount(dto);
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountService.findAll();
    }

}
