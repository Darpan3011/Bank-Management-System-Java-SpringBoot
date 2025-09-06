package com.darpan.finalbankmanagement.audit;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String action;
    
    @Column(nullable = false)
    private String resource;
    
    private String resourceId;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(nullable = false)
    private String ipAddress;
    
    @Column(nullable = false)
    private String userAgent;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    private AuditActionType actionType;
    
    @Enumerated(EnumType.STRING)
    private AuditStatus status;
    
    public enum AuditActionType {
        CREATE, READ, UPDATE, DELETE, LOGIN, LOGOUT, TRANSACTION
    }
    
    public enum AuditStatus {
        SUCCESS, FAILURE, PENDING
    }

    public AuditLog() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setActionType(AuditActionType actionType) {
        this.actionType = actionType;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getResource() {
        return resource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getDetails() {
        return details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public AuditActionType getActionType() {
        return actionType;
    }

    public AuditStatus getStatus() {
        return status;
    }
}
