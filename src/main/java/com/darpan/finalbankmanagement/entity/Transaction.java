package com.darpan.finalbankmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double transactionAmount;
    @Column(nullable = false)
    private double currBalance;
    @Column(nullable = false)
    private double newBalance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    public enum TransactionType {
        WITHDRAW, DEPOSIT
    }


    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getCurrBalance() {
        return currBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setCurrBalance(double currBalance) {
        this.currBalance = currBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
