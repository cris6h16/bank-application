package com.devsu.hackerearth.backend.account.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.error.application.ApplicationErrors;
import com.devsu.hackerearth.backend.account.error.application.ApplicationException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.entities.Account;
import com.devsu.hackerearth.backend.account.model.entities.Transaction;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.validator.TransactionValidator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService,
            AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.TRANSACTION_NOT_FOUND));

        return TransactionMapper.toDto(transaction);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrors.ACCOUNT_NOT_FOUND));

        transactionDto.setId(null);
        transactionDto.setDate(Date.from(Instant.now()));
        transactionDto.setBalance(getCurrentBalance(transactionDto, account) + transactionDto.getAmount());

        TransactionValidator.validateCreation(transactionDto);

        return TransactionMapper.toDto(toEntityAndSave(transactionDto, account));
    }

    private double getCurrentBalance(TransactionDto transactionDto, Account account) {
        if (Objects.isNull(transactionDto) || Objects.isNull(transactionDto.getAccountId())) {
            return 0d;
        }

        Optional<Transaction> latest = transactionRepository
                .findTopByAccountIdOrderByIdDesc(transactionDto.getAccountId());

        if (latest.isPresent()) {
            return latest.get().getBalance();
        }

        return account.getInitialAmount();
    }

    private Transaction toEntityAndSave(TransactionDto transactionDto, Account account) {
        Transaction transaction = TransactionMapper.toEntity(transactionDto, account);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        final List<Transaction> transactions = transactionRepository.findByAccountIdAndDateBetween(clientId,
                dateTransactionStart, dateTransactionEnd);
        final AccountDto accountDto = accountService.getById(clientId);

        return transactions.stream()
                .map(transaction -> ToBankStatement(transaction, accountDto))
                .collect(Collectors.toList());
    }

    private BankStatementDto ToBankStatement(Transaction transaction, AccountDto accountDto) {
        return BankStatementDto.builder()
                .date(transaction.getDate())
                .client(accountDto.getClientId().toString()) // todo ver que valor deberia ir aca
                .accountNumber(accountDto.getNumber())
                .accountType(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .transactionType(transaction.getType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .build();
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
