package com.darpan.finalbankmanagement.controller;

import com.darpan.finalbankmanagement.UserPrincipalService;
import com.darpan.finalbankmanagement.dto.MaxTransactionCount;
import com.darpan.finalbankmanagement.entity.Transaction;
import com.darpan.finalbankmanagement.servicesIml.TransactionServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final UserPrincipalService userPrincipalService;
    private final TransactionServiceImp transactionServiceImp;


    public TransactionController(UserPrincipalService userPrincipalService, TransactionServiceImp transactionServiceImp) {
        this.userPrincipalService = userPrincipalService;
        this.transactionServiceImp = transactionServiceImp;
    }

    @GetMapping
    public ResponseEntity getTransactions(){
        try {
            List<Transaction> transactions = transactionServiceImp.findTransactionByUserId(userPrincipalService.getLoggedInUserId());

            if(transactions.isEmpty() || transactions == null){
                return ResponseEntity.status(404).body("No transactions found");
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("top10")
    public ResponseEntity getTop10Transactions(){
        try {
            List<Transaction> transactions = transactionServiceImp.findTop10ByUserId(userPrincipalService.getLoggedInUserId());

            if(transactions.isEmpty() || transactions == null){
                return ResponseEntity.status(404).body("No transactions found");
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("top")
    public ResponseEntity getTopTransaction(){
        try {
            List<Transaction> transactions =  transactionServiceImp.findTopTransactionByUserId(userPrincipalService.getLoggedInUserId());

            if(transactions.isEmpty() || transactions == null){
                return ResponseEntity.status(404).body("No transactions found");
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("most")
    public ResponseEntity getMaxTransactionCount(){
        try {
            MaxTransactionCount maxTransactionCount = transactionServiceImp.maxTransactionCount();

            if(maxTransactionCount== null){
                return ResponseEntity.status(404).body("No count found");
            }

            return ResponseEntity.ok(maxTransactionCount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
