package com.darpan.finalbankmanagement.rules;

import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class BusinessRulesEngine {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Value("${transaction.daily.limit:10000}")
    private double dailyLimit;
    
    @Value("${transaction.weekly.limit:50000}")
    private double weeklyLimit;
    
    @Value("${transaction.monthly.limit:200000}")
    private double monthlyLimit;
    
    public boolean validateTransaction(UserAccount account, double amount, String transactionType) {
        // Check minimum balance for withdrawals
        if ("WITHDRAW".equals(transactionType) && account.getBankBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        
        // Check daily transaction limit
        if (!checkDailyLimit(account.getId(), amount)) {
            throw new RuntimeException("Daily transaction limit exceeded");
        }
        
        // Check weekly transaction limit
        if (!checkWeeklyLimit(account.getId(), amount)) {
            throw new RuntimeException("Weekly transaction limit exceeded");
        }
        
        // Check monthly transaction limit
        if (!checkMonthlyLimit(account.getId(), amount)) {
            throw new RuntimeException("Monthly transaction limit exceeded");
        }
        
        return true;
    }
    
    private boolean checkDailyLimit(Long accountId, double amount) {
        LocalDateTime startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        double dailyTotal = transactionRepository.getTotalAmountByAccountAndDateRange(
            accountId, startOfDay, LocalDateTime.now());
        return (dailyTotal + amount) <= dailyLimit;
    }
    
    private boolean checkWeeklyLimit(Long accountId, double amount) {
        LocalDateTime startOfWeek = LocalDateTime.now().minusWeeks(1);
        double weeklyTotal = transactionRepository.getTotalAmountByAccountAndDateRange(
            accountId, startOfWeek, LocalDateTime.now());
        return (weeklyTotal + amount) <= weeklyLimit;
    }
    
    private boolean checkMonthlyLimit(Long accountId, double amount) {
        LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(1);
        double monthlyTotal = transactionRepository.getTotalAmountByAccountAndDateRange(
            accountId, startOfMonth, LocalDateTime.now());
        return (monthlyTotal + amount) <= monthlyLimit;
    }
}
