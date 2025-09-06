package com.darpan.finalbankmanagement.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    
    @Autowired
    private AuditRepository auditRepository;
    
    public void logAction(String userId, String action, String resource, String resourceId, 
                         String details, HttpServletRequest request, 
                         AuditLog.AuditActionType actionType, AuditLog.AuditStatus status) {
        
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setResource(resource);
        auditLog.setResourceId(resourceId);
        auditLog.setDetails(details);
        auditLog.setIpAddress(getClientIpAddress(request));
        auditLog.setUserAgent(request.getHeader("User-Agent"));
        auditLog.setActionType(actionType);
        auditLog.setStatus(status);
        
        auditRepository.save(auditLog);
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
