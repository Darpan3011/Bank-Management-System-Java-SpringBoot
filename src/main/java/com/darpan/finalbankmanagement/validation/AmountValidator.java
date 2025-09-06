package com.darpan.finalbankmanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountValidator implements ConstraintValidator<ValidAmount, Double> {
    
    private double min;
    private double max;
    
    @Override
    public void initialize(ValidAmount constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        
        if (value < min || value > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                String.format("Amount must be between %.2f and %.2f", min, max)
            ).addConstraintViolation();
            return false;
        }
        
        // Check for precision (max 2 decimal places)
        if (value * 100 != Math.floor(value * 100)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Amount cannot have more than 2 decimal places"
            ).addConstraintViolation();
            return false;
        }
        
        return true;
    }
}
