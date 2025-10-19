package com.devsu.hackerearth.backend.account.model.mapper;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.entities.Account;

public final class AccountMapper {

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .number(account.getNumber())
                .type(account.getType())
                .initialAmount(account.getInitialAmount())
                .isActive(account.isActive())
                .clientId(account.getClientId())
                .build();
    }

    public static Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .clientId(accountDto.getClientId())
                .build();
    }

}
