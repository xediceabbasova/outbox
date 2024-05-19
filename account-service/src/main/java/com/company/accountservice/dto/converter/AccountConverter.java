package com.company.accountservice.dto.converter;

import com.company.accountservice.dto.CreateAccountDto;
import com.company.accountservice.model.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountConverter {
    public static Account fromDto(CreateAccountDto dto) {
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setMail(dto.getMail());
        account.setPassword(dto.getPassword());
        return account;
    }
}
