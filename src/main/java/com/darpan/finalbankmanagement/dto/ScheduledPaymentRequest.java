package com.darpan.finalbankmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduledPaymentRequest {
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Scheduled payment amount cannot exceed 10,000")
    private Double amount;
    
    @NotNull(message = "Target account ID is required")
    private Long targetAccountId;
    
    @NotNull(message = "Scheduled date is required")
    @Future(message = "Scheduled date must be in the future")
    private LocalDateTime scheduledDate;
    
    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
    
    @Pattern(regexp = "^(ONCE|WEEKLY|MONTHLY)$", message = "Frequency must be ONCE, WEEKLY, or MONTHLY")
    private String frequency = "ONCE";
}
