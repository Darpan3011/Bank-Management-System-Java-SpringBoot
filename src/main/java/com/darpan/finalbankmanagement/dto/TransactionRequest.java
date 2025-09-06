package com.darpan.finalbankmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransactionRequest {
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "100000.0", message = "Amount cannot exceed 100,000")
    private Double amount;
    
    @NotBlank(message = "Transaction type is required")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAW|TRANSFER)$", message = "Transaction type must be DEPOSIT, WITHDRAW, or TRANSFER")
    private String transactionType;
    
    private String description;
    
    // For transfer transactions
    private Long targetAccountId;
}
