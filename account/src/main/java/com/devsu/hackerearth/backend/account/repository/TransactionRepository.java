package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdAndDateBetween(Long id, Date dateTransactionStart,
            Date dateTransactionEnd);

    void deleteByAccountId(Long accountId);

    Optional<Transaction> findTopByAccountIdOrderByIdDesc(Long accountId);
}
