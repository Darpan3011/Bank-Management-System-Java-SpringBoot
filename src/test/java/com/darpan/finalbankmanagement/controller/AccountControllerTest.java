package com.darpan.finalbankmanagement.controller;

import com.darpan.finalbankmanagement.dto.AccountCreateRequest;
import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.servicesIml.AccountServiceImp;
import com.darpan.finalbankmanagement.UserPrincipalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountServiceImp accountService;
    
    @MockBean
    private UserPrincipalService userPrincipalService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testCreateAccount_Success() throws Exception {
        // Given
        AccountCreateRequest request = new AccountCreateRequest();
        request.setAccountHolderName("John Doe");
        request.setEmail("john@example.com");
        request.setPhoneNumber("+1234567890");
        request.setInitialBalance(1000.0);
        request.setAddress("123 Main St");
        
        UserAccount mockAccount = new UserAccount();
        mockAccount.setId(1L);
        mockAccount.setFirstName("John");
        mockAccount.setLastName("Doe");
        mockAccount.setEmail("john@example.com");
        mockAccount.setPhoneNumber("+1234567890");
        mockAccount.setBankBalance(1000.0);
        mockAccount.setAddress("123 Main St");
        
        when(accountService.createAccount(any(UserAccount.class))).thenReturn(mockAccount);
        
        // When & Then
        mockMvc.perform(post("/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolderName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
    
    @Test
    public void testCreateAccount_ValidationError() throws Exception {
        // Given
        AccountCreateRequest request = new AccountCreateRequest();
        request.setAccountHolderName(""); // Invalid: empty name
        request.setEmail("invalid-email"); // Invalid: bad email format
        request.setPhoneNumber("123"); // Invalid: too short
        request.setInitialBalance(-100.0); // Invalid: negative amount
        
        // When & Then
        mockMvc.perform(post("/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
