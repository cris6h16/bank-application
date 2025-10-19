package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.error.application.ApplicationErrors;
import com.devsu.hackerearth.backend.account.error.application.ApplicationException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.entities.Account;
import com.devsu.hackerearth.backend.account.model.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.validator.AccountValidator;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.ACCOUNT_NOT_FOUND));

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        AccountValidator.validateCreation(accountDto);
        accountDto.setId(null);
        Account saved = accountRepository.save(AccountMapper.toEntity(accountDto));
        return AccountMapper.toDto(saved);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        AccountValidator.validateUpdate(accountDto);

        Account account = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.ACCOUNT_NOT_FOUND));

        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return AccountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.ACCOUNT_NOT_FOUND));

        account.setActive(partialAccountDto.isActive());
        return AccountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.ACCOUNT_NOT_FOUND));

        accountRepository.delete(account);
        transactionRepository.deleteByAccountId(account.getId());
    }

    @Override
    public void deleteByClientId(Long clientId) {
        List<Account> accounts = accountRepository.findByClientId(clientId);
        for (Account account : accounts) {
            transactionRepository.deleteByAccountId(account.getId());
        }
        accountRepository.deleteAll(accounts);
    }

}
