package com.darpan.finalbankmanagement;

import com.darpan.finalbankmanagement.entity.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipalService {

    public UserPrincipal getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new IllegalStateException("User not authenticated");
        }
        return (UserPrincipal) authentication.getPrincipal();
    }

    public Long getLoggedInUserId() {
        return getLoggedInUser().getId();
    }
}
