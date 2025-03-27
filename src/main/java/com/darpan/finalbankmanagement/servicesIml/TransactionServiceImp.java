package com.darpan.finalbankmanagement.servicesIml;

import com.darpan.finalbankmanagement.dto.MaxTransactionCount;
import com.darpan.finalbankmanagement.entity.Transaction;
import com.darpan.finalbankmanagement.repository.TransactionRepository;
import com.darpan.finalbankmanagement.services.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImp  implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImp(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findTransactionByUserId(Long userId) {
        try {
            return transactionRepository.getTransactionsByUser_Id(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting transactions");
        }
    }

    @Override
    public List<Transaction> findTop10ByUserId(Long accountId) {
        try {
            return transactionRepository.getTop10TransactionsByUser_Id(accountId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting top 10 transactions");
        }
    }

    @Override
    public List<Transaction> findTopTransactionByUserId(Long accountId) {
        try {
            Sort sort = Sort.by(Sort.Order.desc("transactionAmount"));
            Pageable pageable = PageRequest.of(0, 1, sort);
            return transactionRepository.findTopByUserIdOrderByTransactionAmountDesc(accountId, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error getting top 10 transactions");
        }
    }

    @Override
    public MaxTransactionCount maxTransactionCount() {
        try {
            return transactionRepository.countTransactionByUser_IdOrderByCountDesc();
        } catch (Exception e) {
            throw new RuntimeException("Error getting max transaction count");
        }
    }
}
