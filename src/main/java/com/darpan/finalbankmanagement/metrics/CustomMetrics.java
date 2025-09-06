package com.darpan.finalbankmanagement.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {
    
    private final Counter transactionCounter;
    private final Counter accountCreationCounter;
    private final Timer transactionTimer;
    private final Counter errorCounter;
    
    @Autowired
    public CustomMetrics(MeterRegistry meterRegistry) {
        this.transactionCounter = Counter.builder("bank.transactions.total")
                .description("Total number of transactions")
                .register(meterRegistry);
        
        this.accountCreationCounter = Counter.builder("bank.accounts.created")
                .description("Total number of accounts created")
                .register(meterRegistry);
        
        this.transactionTimer = Timer.builder("bank.transaction.duration")
                .description("Transaction processing time")
                .register(meterRegistry);
        
        this.errorCounter = Counter.builder("bank.errors.total")
                .description("Total number of errors")
                .register(meterRegistry);
    }
    
    public void incrementTransactionCount() {
        transactionCounter.increment();
    }
    
    public void incrementAccountCreationCount() {
        accountCreationCounter.increment();
    }
    
    public Timer.Sample startTransactionTimer() {
        return Timer.start();
    }
    
    public void recordTransactionTime(Timer.Sample sample) {
        sample.stop(transactionTimer);
    }
    
    public void incrementErrorCount() {
        errorCounter.increment();
    }
}
