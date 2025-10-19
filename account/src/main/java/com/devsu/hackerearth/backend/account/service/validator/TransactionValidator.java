package com.devsu.hackerearth.backend.account.service.validator;

import com.devsu.hackerearth.backend.account.error.application.ApplicationErrors;
import com.devsu.hackerearth.backend.account.error.application.ApplicationException;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionValidator {

    public static void validateCreation(TransactionDto transactionDto) {
        if (transactionDto.getBalance() < 0) {
            throw new ApplicationException(ApplicationErrors.INSUFFICIENT_BALANCE);
        }
        if (transactionDto.getAmount() == 0) {
            throw new ApplicationException(ApplicationErrors.INVALID_AMOUNT);
        }
    }
}
