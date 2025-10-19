package com.devsu.hackerearth.backend.account.error.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrors {
    ACCOUNT_NOT_FOUND("Account not found", "ACCO001"),
    TRANSACTION_NOT_FOUND("Transaction not found", "TRAN001"), 
    INSUFFICIENT_BALANCE("Saldo no disponible", "TRAN002"),
    INVALID_AMOUNT("Invalid amount", "TRAN003");
    
    private String message;
    private String code;
}
