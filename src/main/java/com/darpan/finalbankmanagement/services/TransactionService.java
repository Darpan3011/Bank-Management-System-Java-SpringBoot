package com.darpan.finalbankmanagement.services;

import com.darpan.finalbankmanagement.dto.MaxTransactionCount;
import com.darpan.finalbankmanagement.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findTransactionByUserId(Long userId);

    List<Transaction> findTop10ByUserId(Long accountId);

    List<Transaction> findTopTransactionByUserId(Long accountId);

    MaxTransactionCount maxTransactionCount();
}
