package com.darpan.finalbankmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransferRequest {
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "50000.0", message = "Transfer amount cannot exceed 50,000")
    private Double amount;
    
    @NotNull(message = "Target account ID is required")
    private Long targetAccountId;
    
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
}
