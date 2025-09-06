package com.darpan.finalbankmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_payments")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ScheduledPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long fromAccountId;
    
    @Column(nullable = false)
    private Long toAccountId;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private LocalDateTime scheduledDate;
    
    @Column(nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private PaymentFrequency frequency;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime lastExecuted;
    
    public enum PaymentFrequency {
        ONCE, WEEKLY, MONTHLY
    }
    
    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }
}
