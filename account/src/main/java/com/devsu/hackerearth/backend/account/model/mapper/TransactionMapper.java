package com.devsu.hackerearth.backend.account.model.mapper;

import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.entities.Account;
import com.devsu.hackerearth.backend.account.model.entities.Transaction;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionDto.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .accountId(transaction.getAccount() != null ? transaction.getAccount().getId() : null)
                .build();
    }

    public static Transaction toEntity(TransactionDto dto, Account account) {
        if (dto == null) {
            return null;
        }

        return Transaction.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .type(dto.getType())
                .amount(dto.getAmount())
                .balance(dto.getBalance())
                .account(account)
                .build();
    }

}
