package com.darpan.finalbankmanagement.repository;

import com.darpan.finalbankmanagement.dto.MaxTransactionCount;
import com.darpan.finalbankmanagement.entity.Transaction;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionsByUser_Id(Long userId);

    List<Transaction> getTop10TransactionsByUser_Id(Long userId);

    List<Transaction> findTopByUserIdOrderByTransactionAmountDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT COUNT(t.id) AS countNum, t.user_id AS Id FROM transactions t GROUP BY t.user_id ORDER BY countNum DESC LIMIT 1", nativeQuery = true)
    MaxTransactionCount countTransactionByUser_IdOrderByCountDesc();
    
    @Query("SELECT COALESCE(SUM(t.transactionAmount), 0) FROM Transaction t WHERE t.user.id = :accountId AND t.transactionDate BETWEEN :startDate AND :endDate")
    Double getTotalAmountByAccountAndDateRange(@Param("accountId") Long accountId, 
                                            @Param("startDate") LocalDateTime startDate, 
                                            @Param("endDate") LocalDateTime endDate);
}
